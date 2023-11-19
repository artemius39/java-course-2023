package edu.project3;

public class InvalidLogFormatException extends RuntimeException {

    public InvalidLogFormatException(String message) {
        super(message);
    }

    public InvalidLogFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
