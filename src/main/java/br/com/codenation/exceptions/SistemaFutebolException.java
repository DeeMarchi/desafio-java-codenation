package br.com.codenation.exceptions;

public class SistemaFutebolException extends RuntimeException {

    public SistemaFutebolException() {
        super();
    }

    public SistemaFutebolException(String message) {
        super(message);
    }
}
