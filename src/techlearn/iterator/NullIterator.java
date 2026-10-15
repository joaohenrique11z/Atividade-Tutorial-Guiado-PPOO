package techlearn.iterator;

import java.util.NoSuchElementException;

// Iterador vazio usado pelas folhas: nunca tem próximo elemento.
public class NullIterator<T> implements Iterator<T> {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        throw new NoSuchElementException("Uma aula não possui itens internos.");
    }
}
