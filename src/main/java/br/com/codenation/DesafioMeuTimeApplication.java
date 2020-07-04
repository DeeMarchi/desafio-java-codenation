package br.com.codenation;

import br.com.codenation.exceptions.*;
import br.com.codenation.models.EntidadeIndexavel;
import br.com.codenation.models.Jogador;
import br.com.codenation.models.Time;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DesafioMeuTimeApplication implements MeuTimeInterface {

    private List<Time> listaTimes = new ArrayList<>();
    private List<Jogador> listaJogadores = new ArrayList<>();

    private void validarTimeExiste(Long idBusca) {
        if (buscarTimes().stream()
                .noneMatch(timeId -> timeId.equals(idBusca))) {
            throw new TimeNaoEncontradoException();
        }
    }

    private <T extends EntidadeIndexavel, Texcecao extends SistemaFutebolException>
    String buscarNome(Long id, List<T> lista, Texcecao excecao)
            throws Texcecao {
        T itemParaBusca;
        itemParaBusca = lista.stream()
                .filter(entidade -> entidade.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (itemParaBusca == null) {
            throw excecao;
        }
        return itemParaBusca.toString();
    }

    private Jogador buscarJogadorPorId(Long idJogador) {
        return listaJogadores.stream()
                .filter(jogador -> jogador.getId().equals(idJogador))
                .findFirst()
                .orElseThrow(JogadorNaoEncontradoException::new);
    }

    public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
        Time novoTime = new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario);
        if (buscarTimes().stream()
                .anyMatch(timeId -> timeId.equals(novoTime.getId()))) {
            throw new IdentificadorUtilizadoException();
        }
        listaTimes.add(novoTime);
    }

    public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
        Jogador novoJogador = new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario);
        validarTimeExiste(idTime);
        if (buscarJogadoresDoTime(novoJogador.getIdTime()).stream()
                .anyMatch(jogadorId -> jogadorId.equals(novoJogador.getId()))) {
            throw new IdentificadorUtilizadoException();
        }
        listaJogadores.add(novoJogador);
    }

    public void definirCapitao(Long idJogador) {
        Jogador jogadorParaPromover = buscarJogadorPorId(idJogador);
        Time timeParaDefinirCapitao = listaTimes.stream()
                .filter(time -> time.getId().equals(jogadorParaPromover.getIdTime()))
                .findFirst()
                .orElseThrow(TimeNaoEncontradoException::new);
        timeParaDefinirCapitao.setCapitao(jogadorParaPromover);
    }

    public Long buscarCapitaoDoTime(Long idTime) {
        Time timeBusca = listaTimes.stream()
                .filter(time -> time.getId().equals(idTime))
                .findFirst()
                .orElseThrow(TimeNaoEncontradoException::new);
        Jogador capitao = timeBusca.getCapitao();
        if (capitao == null) {
            throw new CapitaoNaoInformadoException();
        }
        return capitao.getId();
    }

    public String buscarNomeJogador(Long idJogador) {
        JogadorNaoEncontradoException excecao;
        excecao = new JogadorNaoEncontradoException();
        return buscarNome(idJogador, listaJogadores, excecao);
    }

    public String buscarNomeTime(Long idTime) {
        TimeNaoEncontradoException excecao;
        excecao = new TimeNaoEncontradoException();
        return buscarNome(idTime, listaTimes, excecao);
    }

    public List<Long> buscarJogadoresDoTime(Long idTime) {
        validarTimeExiste(idTime);
        return listaJogadores.stream()
                .filter(jogador -> jogador.getIdTime().equals(idTime))
                .map(Jogador::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Long buscarMelhorJogadorDoTime(Long idTime) {
        validarTimeExiste(idTime);
        Jogador jogadorBusca = listaJogadores.stream()
                .filter(jogador -> jogador.getIdTime().equals(idTime))
                .max(Comparator.comparing(Jogador::getNivelHabilidade))
                .orElseThrow(JogadorNaoEncontradoException::new);
        return jogadorBusca.getId();
    }

    public Long buscarJogadorMaisVelho(Long idTime) {
        validarTimeExiste(idTime);
        Jogador jogadorBusca = listaJogadores.stream()
                .filter(jogador -> jogador.getIdTime().equals(idTime))
                .min(Comparator.comparing(Jogador::getDataNascimento))
                .orElseThrow(JogadorNaoEncontradoException::new);
        return jogadorBusca.getId();
    }

    public List<Long> buscarTimes() {
        return listaTimes.stream()
                .map(Time::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Long buscarJogadorMaiorSalario(Long idTime) {
        validarTimeExiste(idTime);
        Jogador jogadorBusca = listaJogadores.stream()
                .filter(jogador -> jogador.getIdTime().equals(idTime))
                .max(Comparator.comparing(Jogador::getSalario))
                .orElseThrow(JogadorNaoEncontradoException::new);
        return jogadorBusca.getId();
    }

    public BigDecimal buscarSalarioDoJogador(Long idJogador) {
        return buscarJogadorPorId(idJogador).getSalario();
    }

    public List<Long> buscarTopJogadores(Integer top) {
        return listaJogadores.stream()
                .map(EntidadeIndexavel::getId)
                .sorted((Comparator.reverseOrder()))
                .limit(top)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
