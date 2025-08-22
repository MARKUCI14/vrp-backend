package edu.bbte.pmim2290.vrp.exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(final String message) {
        super(message);
    }
}
