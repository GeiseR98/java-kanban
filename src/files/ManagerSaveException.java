package files;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException() {

    }
    public ManagerSaveException(final String message) {
        super(message);
    }
}
