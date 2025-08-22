package edu.bbte.pmim2290.vrp.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}
