package br.com.codenation.exceptions;

public class TimeNaoEncontradoException extends SistemaFutebolException{

    public TimeNaoEncontradoException() {
        super();
    }

    public TimeNaoEncontradoException(String message) {
        super(message);
    }
}
