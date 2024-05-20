import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static int PORT = 2341;
    private static String DB_URL = "jdbc:sqlite:date\\contacte.db";

    static Object mutex = new Object();

    private static List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();

        try(
                var connection = DriverManager.getConnection(DB_URL);
                var select = connection.createStatement();
                var cursor = select.executeQuery("SELECT * FROM CONTACTE");
                ) {

            while (cursor.next()) {
                int code = cursor.getInt("Cod");
                String name = cursor.getString("Nume");
                String phone = cursor.getString("Telefon");

                contacts.add(new Contact(code, name, phone));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return contacts;
    }

    private static void addContact(Contact contact) {
        try (
                var connection = DriverManager.getConnection(DB_URL);
                var insert = connection.prepareStatement(
                        "INSERT INTO CONTACTE VALUES (?, ?, ?)")
                ) {
            insert.setInt(1, contact.getCode());
            insert.setString(2, contact.getName());
            insert.setString(3, contact.getPhone());

            insert.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void processServerConnection(Socket socket) {
        try (
                var in = new ObjectInputStream(socket.getInputStream());
                var out = new ObjectOutputStream(socket.getOutputStream())
                ) {

            while (true) {
                var payload = (TransferableMessage)in.readObject();

                System.out.println("Current thread: "
                        + Thread.currentThread().getId() + " received: " + payload);

                if ("Add".equals(payload.message)) {
                    synchronized (mutex) {
                        var contactToAdd = (Contact) payload.payload;

                        int uniqueCode = getContacts()
                                .stream()
                                .map(Contact::getCode)
                                .max(Integer::compareTo)
                                .orElse(0) + 1;

                        addContact(
                                new Contact(uniqueCode,
                                        contactToAdd.getName(),
                                        contactToAdd.getPhone()));

                        out.writeObject(contactToAdd);
                    }
                } else if ("List".equals(payload.message)) {
                    out.writeObject(getContacts());
                } else if ("Exit".equals(payload.message)) {
                    break;
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
//        System.out.println(getContacts());
//        addContact(new Contact(100, "sdfsdfasd", "dfasdfa"));
//        System.out.println(getContacts());

        try (var server = new ServerSocket(PORT)) {
            while (true) {
                var socket = server.accept();
                new Thread(() -> processServerConnection(socket)).start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
