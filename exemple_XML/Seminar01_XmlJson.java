import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

enum TipTranzactie {
    INTRARE(1),
    IESIRE(-1);

    private final int semn;

    TipTranzactie(int semn) {
        this.semn = semn;
    }

    public int getSemn() {
        return semn;
    }
}

class Tranzactie {
    private final TipTranzactie tip;
    private final int cantitate;

    public Tranzactie(TipTranzactie tip, int cantitate) {
        this.tip = tip;
        this.cantitate = cantitate;
    }

    public TipTranzactie getTip() {
        return tip;
    }

    public int getCantitate() {
        return cantitate;
    }

    public int getDeltaStoc() {
        return cantitate * tip.getSemn();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tranzactie{");
        sb.append("tip=").append(tip);
        sb.append(", cantitate=").append(cantitate);
        sb.append('}');
        return sb.toString();
    }
}

class Produs {
    private final int cod;
    private final String denumire;
    private final List<Tranzactie> tranzactii;

    public Produs(int cod, String denumire, List<Tranzactie> tranzactii) {
        this.cod = cod;
        this.denumire = denumire;
        this.tranzactii = Collections.unmodifiableList(tranzactii);
    }

    public int getCod() {
        return cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public List<Tranzactie> getTranzactii() {
        return tranzactii;
    }

    public int getStoc() {
        return tranzactii.stream()
                .mapToInt(Tranzactie::getDeltaStoc)
                .sum();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Produs{");
        sb.append("cod=").append(cod);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", tranzactii=").append(tranzactii);
        sb.append('}');
        return sb.toString();
    }
}

public class Seminar01_XmlJson {

    public static List<Produs> citireXml(String caleFisier) throws Exception {
        var rezultat = new ArrayList<Produs>();

        var factory = DocumentBuilderFactory.newInstance();
        var builder = factory.newDocumentBuilder();

        // 2. Citim conținutul din fișier
        var document = builder.parse(caleFisier);
        var radacina = document.getDocumentElement();

//        var nodProdus0 = (Element)radacina.getElementsByTagName("produs").item(0);
//
//        System.out.println(nodProdus0.getElementsByTagName("denumire").item(0).getTextContent());


        var noduriProdus = radacina.getElementsByTagName("produs");
        for (int index = 0; index < noduriProdus.getLength(); index++) {
            Element nodProdus = (Element) noduriProdus.item(index);

            int cod = Integer.parseInt(nodProdus
                    .getElementsByTagName("cod")
                    .item(0)
                    .getTextContent());

            String denumire = nodProdus
                    .getElementsByTagName("denumire")
                    .item(0)
                    .getTextContent();

            var nodTranzactii = (Element) nodProdus
                    .getElementsByTagName("tranzactii")
                    .item(0);

            var tranzactii = new ArrayList<Tranzactie>();
            var noduriTranzactie = nodTranzactii.getElementsByTagName("tranzactie");
            for (int indexTranzactii = 0; indexTranzactii < noduriTranzactie.getLength(); indexTranzactii++) {
                var nodTranzactie = (Element) noduriTranzactie.item(indexTranzactii);
                var tranzactie = new Tranzactie(
                        TipTranzactie.valueOf(nodTranzactie.getAttribute("tip").toUpperCase()),
                        Integer.parseInt(nodTranzactie.getAttribute("cantitate"))
                );
                tranzactii.add(tranzactie);
            }

            rezultat.add(new Produs(cod, denumire, tranzactii));
        }

        return rezultat;
    }

    public static void salvareXml(String caleFisier, List<Produs> produse) throws Exception {
        // 1. Construire document XML gol
        var factory = DocumentBuilderFactory.newInstance();
        var builder = factory.newDocumentBuilder();

        var document = builder.newDocument();

        // 2. Construire și atașare elemente
        var radacina = document.createElement("produse");
        document.appendChild(radacina);

        for (var produs : produse) {
            var nodProdus = document.createElement("produs");
            radacina.appendChild(nodProdus);

            var nodCod = document.createElement("cod");
            nodCod.setTextContent(Integer.toString(produs.getCod()));
            nodProdus.appendChild(nodCod);

            var nodDenumire = document.createElement("denumire");
            nodDenumire.setTextContent(produs.getDenumire());
            nodProdus.appendChild(nodDenumire);

            var nodTranzactii = document.createElement("tranzactii");
            for (var tranzactie : produs.getTranzactii()) {
                var nodTranzactie = document.createElement("tranzactie");
                nodTranzactie.setAttribute("tip", tranzactie.getTip().toString().toLowerCase());
                nodTranzactie.setAttribute("cantitate", Integer.toString(tranzactie.getCantitate()));
                nodTranzactii.appendChild(nodTranzactie);
            }
            nodProdus.appendChild(nodTranzactii);
        }

        // 3. Salvare XML în fișier
        var transformerFactory = TransformerFactory.newInstance();
        var transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        try (var fisier = new FileOutputStream(caleFisier)) {
            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(fisier));
        }
    }

    public static void salvareJson(String caleFisier, List<Produs> produse) throws Exception {

        var jsonProduse = new JSONArray();

        for (var produs : produse) {

            var jsonProdus = new JSONObject();
            jsonProdus.put("cod", produs.getCod());
            jsonProdus.put("denumire", produs.getDenumire());

            var jsonTranzactii = new JSONArray();
            for (var tranzactie : produs.getTranzactii()) {
                var jsonTranzactie = new JSONObject();
                jsonTranzactie.put("tip", tranzactie.getTip().toString().toLowerCase());
                jsonTranzactie.put("cantitate", Integer.toString(tranzactie.getCantitate()));
                jsonTranzactii.put(jsonTranzactie);
            }
            jsonProdus.put("tranzactii", jsonTranzactii);

            jsonProduse.put(jsonProdus);
        }

        try (var fisier = new FileWriter(caleFisier)) {
            jsonProduse.write(fisier, 3, 0);
        }
    }

    public static List<Produs> citireJson(String caleFisier) throws Exception {
        var rezultat = new ArrayList<Produs>();

        try (var fisier = new FileInputStream(caleFisier)) {

            var tokener = new JSONTokener(fisier);
            var jsonProduse = new JSONArray(tokener);

            for (int index = 0; index < jsonProduse.length(); index++) {
                var jsonProdus = jsonProduse.getJSONObject(index);

                var tranzactii = StreamSupport
                        .stream(jsonProdus.getJSONArray("tranzactii").spliterator(), false)
                        .map(item -> (JSONObject) item)
                        .map(item -> new Tranzactie(
                                TipTranzactie.valueOf(item.getString("tip").toUpperCase()),
                                item.getInt("cantitate")
                        ))
                        .collect(Collectors.toList());

                rezultat.add(new Produs(
                        jsonProdus.getInt("cod"),
                        jsonProdus.getString("denumire"),
                        tranzactii
                ));
            }
        }

        return rezultat;
    }

    public static void main(String[] args) throws Exception {

        // Testare clase:
//        var p = new Produs(1, "test", Arrays.asList(
//                new Tranzactie(TipTranzactie.INTRARE, 10),
//                new Tranzactie(TipTranzactie.INTRARE, 5),
//                new Tranzactie(TipTranzactie.IESIRE, 11)
//        ));
//        System.out.println(p);
//        System.out.println(p.getStoc());
        var produse = citireXml("date\\produse.xml");

        salvareXml("date\\produse_generat.xml", produse);
        salvareJson("date\\produse_generat.json", produse);
        produse = citireJson("date\\produse_generat.json");
        System.out.println(produse);
    }



}
