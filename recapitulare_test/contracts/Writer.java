package contracts;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Writer<T> {
    public void write(String filePath, T data) throws IOException;
}
