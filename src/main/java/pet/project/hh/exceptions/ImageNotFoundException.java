package pet.project.hh.exceptions;

import java.util.NoSuchElementException;

public class ImageNotFoundException extends Exception {
    public ImageNotFoundException() {
        super("Image not found");
    }
}
