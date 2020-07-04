package br.com.codenation.models;

public abstract class EntidadeIndexavel {

    private Long id;

    public EntidadeIndexavel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public abstract String toString();
}
