package techlearn.iterator;

import techlearn.composite.ComponenteCatalogo;
import java.util.NoSuchElementException;

// Iterador para módulos que guardam os filhos em um array em vez de uma List.
public class ArrayModuloIterator implements Iterator<ComponenteCatalogo> {
    private final ComponenteCatalogo[] itens;
    private final int quantidade;
    private int posicao = 0;

    // "quantidade" é quantas posições do array estão ocupadas (o resto é espaço livre).
    public ArrayModuloIterator(ComponenteCatalogo[] itens, int quantidade) {
        this.itens = itens;
        this.quantidade = quantidade;
    }

    @Override
    public boolean hasNext() {
        return posicao < quantidade;
    }

    @Override
    public ComponenteCatalogo next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Não há mais itens neste módulo.");
        }
        return itens[posicao++];
    }
}
