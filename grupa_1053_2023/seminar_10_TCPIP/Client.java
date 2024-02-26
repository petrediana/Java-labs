import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Client {
    static final String SERVER_NAME = "localhost";
    static final int SERVER_PORT = 2341;

    static void pornesteClient(){
        try (var socket = new Socket(SERVER_NAME, SERVER_PORT);
             var out = new ObjectOutputStream(socket.getOutputStream());
             var in = new ObjectInputStream(socket.getInputStream())) {

            // Mesajul 1. Adăugăm un contact
            var contactTest = new Contact(0, "Test", "123");
            out.writeObject(
                    new Comanda("adauga", contactTest));
            var contactAdaugat = (Contact)in.readObject();
            System.out.println(
                    "Contactul a fost adăugat - Cod: " + contactAdaugat.getCod());

            // Mesajul 2. Obținem lista actualizată
            out.writeObject(new Comanda("lista"));
            var raspuns = (List<Contact>) in.readObject();
            System.out.println("Lista server:");
            raspuns.forEach(item -> System.out.println("   " + item));

            out.writeObject(new Comanda("exit"));
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 5; i++) {
            new Thread(Client::pornesteClient).start();
        }
    }
}
