package pet.project.hh.exceptions;

public class AccessDeniedException extends Exception {
    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException() {
        super("{Access.denied}");
    }
}
