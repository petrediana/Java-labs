import java.io.Serializable;
public class TransferableMessage implements Serializable {
    public String message;
    public Object payload;

    public TransferableMessage(String message, Object payload) {
        this.message = message;
        this.payload = payload;
    }

    public TransferableMessage(String message) {
        this.message = message;
        this.payload = null;
    }

    @java.lang.Override
    public String toString() {
        return "TransferableMessage{" +
                "message='" + message + '\'' +
                ", payload=" + payload +
                '}';
    }
}
