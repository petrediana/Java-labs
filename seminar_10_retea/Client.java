import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Client {
    private static void start() {
        try(
                var socket = new Socket("localhost", 2341);
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream())
                ) {

            Contact newContact = new Contact(101, "Name", "132231");
            out.writeObject(
                    new TransferableMessage("Add", newContact)
            );

            var fromServer = (Contact)in.readObject();
            System.out.println("Contact from server: " + fromServer);

            out.writeObject(
                    new TransferableMessage("List")
            );

            var fromServerList = (List<Contact>) in.readObject();
            System.out.println(fromServerList);

            out.writeObject(new TransferableMessage("Exit"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; ++i) {
            new Thread(Client::start).start();
        }
    }
}
