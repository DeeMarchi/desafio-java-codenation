package br.com.codenation.exceptions;

public class JogadorNaoEncontradoException extends SistemaFutebolException {

    public JogadorNaoEncontradoException() {
        super();
    }

    public JogadorNaoEncontradoException(String message) {
        super(message);
    }
}
