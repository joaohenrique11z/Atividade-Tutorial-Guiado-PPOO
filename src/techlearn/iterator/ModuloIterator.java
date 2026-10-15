package techlearn.iterator;

import techlearn.composite.ComponenteCatalogo;
import java.util.List;
import java.util.NoSuchElementException;

public class ModuloIterator implements Iterator<ComponenteCatalogo> {
    private List<ComponenteCatalogo> itens;
    private int posicao = 0;

    public ModuloIterator(List<ComponenteCatalogo> itens) {
        this.itens = itens;
    }

    @Override
    public boolean hasNext() {
        return posicao < itens.size();
    }

    @Override
    public ComponenteCatalogo next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Não há mais itens neste módulo.");
        }
        ComponenteCatalogo item = itens.get(posicao);
        posicao++;
        return item;
    }
}
