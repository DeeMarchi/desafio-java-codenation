package br.com.codenation.exceptions;

public class CapitaoNaoInformadoException extends RuntimeException {

    public CapitaoNaoInformadoException() {
        super();
    }

    public CapitaoNaoInformadoException(String message) {
        super(message);
    }
}
