import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;

public class Program02_SalvareXML {
    public static void main(String[] args) throws Exception {

        // 1. Construire document XML gol
        var factory = DocumentBuilderFactory.newInstance();
        var builder = factory.newDocumentBuilder();

        var document = builder.newDocument();

        // 2. Construire și atașare elemente
        var radacina = document.createElement("radacina");
        document.appendChild(radacina);

        var element1 = document.createElement("element1");
        element1.setTextContent("un text..");
        radacina.appendChild(element1);

        var element2 = document.createElement("element2");
        element2.setAttribute("nume", "valoare");
        radacina.appendChild(element2);

        // 3. Salvare XML în fișier
        var transformerFactory = TransformerFactory.newInstance();
        var transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        try (var fisier = new FileOutputStream("date\\test.xml")){
            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(fisier));
        }
    }
}
