package tsk;

import java.io.IOException;

public interface IConverter {
    void read() throws IOException;
    void save() throws IOException;
}
