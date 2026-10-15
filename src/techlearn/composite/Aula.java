package techlearn.composite;

import techlearn.iterator.Iterator;
import techlearn.iterator.NullIterator;

public class Aula extends ComponenteCatalogo {
    private String nome;
    private int duracaoMinutos;

    public Aula(String nome, int duracaoMinutos) {
        this.nome = nome;
        this.duracaoMinutos = duracaoMinutos;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("  - Aula: " + getNome() + " (" + getDuracaoMinutos() + " min)");
    }

    @Override
    public Iterator<ComponenteCatalogo> criarIterator() {
        return new NullIterator<>();
    }

    @Override
    public Iterator<ComponenteCatalogo> criarIteratorCompleto() {
        return new NullIterator<>();
    }
}
