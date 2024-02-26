import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static final int SERVER_PORT = 2341;
    static final String DB_URL = "jdbc:sqlite:date\\contacte.db";

    static List<Contact> contacte;
    static Object lockContacte;

    static List<Contact> citireContacte() {
        var rezultat = new ArrayList<Contact>();
        try (var conexiune = DriverManager.getConnection(DB_URL);
             var comanda = conexiune.createStatement();
             var rs = comanda.executeQuery("SELECT * FROM Contacte");
        ) {
            while (rs.next()) {
                rezultat.add(new Contact(
                        rs.getInt("Cod"),
                        rs.getString("Nume"),
                        rs.getString("Telefon")));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return rezultat;
    }

    static void adaugareContact(Contact contact) {
        try (var conexiune = DriverManager.getConnection(DB_URL);
             var comanda = conexiune.prepareStatement("INSERT INTO Contacte VALUES(?, ?, ?)");
        ) {
            comanda.setInt(1, contact.getCod());
            comanda.setString(2, contact.getNume());
            comanda.setString(3, contact.getTelefon());
            comanda.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void procesareConexiune(Socket clientSocket) {
        try (Socket socket = clientSocket;
             var in = new ObjectInputStream(socket.getInputStream());
             var out = new ObjectOutputStream(socket.getOutputStream());
        ) {
            while (true) {
                var comanda = (Comanda) in.readObject();

                System.out.println(Thread.currentThread().getId() + " - Am primit comanda: " + comanda);

                if (comanda.denumire.equals("lista")) {
                    out.writeObject(contacte);
                } else if (comanda.denumire.equals("adauga")) {
                    synchronized (lockContacte) {
                        int cod = 1 + contacte.stream()
                                .map(Contact::getCod)
                                .max(Integer::compareTo).orElse(0);
                        var contact = (Contact) comanda.parametru;
                        contact.setCod(cod);
                        adaugareContact(contact);
                        contacte = citireContacte();
                        out.writeObject(contact);
                    }
                } else if (comanda.denumire.equals("exit")) {
                    break;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        contacte = citireContacte();
        lockContacte = new Object();

        try (var server = new ServerSocket(SERVER_PORT)) {
            while (true) {
                var socket = server.accept();
                new Thread(() -> procesareConexiune(socket)).start();
            }
        }
    }
}
