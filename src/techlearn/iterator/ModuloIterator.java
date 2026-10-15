package techlearn.iterator;

import techlearn.composite.ComponenteCatalogo;
import java.util.List;

public class ModuloIterator implements Iterator<ComponenteCatalogo> {
    private List<ComponenteCatalogo> itens;
    private int posicao = 0;

    public ModuloIterator(List<ComponenteCatalogo> itens) {
        this.itens = itens;
    }

    @Override
    public boolean hasNext() {
        return posicao < itens.size() && itens.get(posicao) != null;
    }

    @Override
    public ComponenteCatalogo next() {
        ComponenteCatalogo item = itens.get(posicao);
        posicao++;
        return item;
    }
}
