package contracts;

import java.io.IOException;

public interface Reader<T> {
    public T read(String filePath) throws IOException;
}
