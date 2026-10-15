package techlearn.iterator;

import techlearn.composite.ComponenteCatalogo;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

// Percorre todos os descendentes em profundidade (DFS), em pré-ordem.
// O topo da pilha é sempre o iterador do nível que está sendo visitado.
public class CompositeIterator implements Iterator<ComponenteCatalogo> {
    private final Deque<Iterator<ComponenteCatalogo>> pilha = new ArrayDeque<>();

    public CompositeIterator(Iterator<ComponenteCatalogo> iteradorRaiz) {
        pilha.push(iteradorRaiz);
    }

    @Override
    public boolean hasNext() {
        // Descarta os níveis que já terminaram até achar um com itens.
        while (!pilha.isEmpty()) {
            if (pilha.peek().hasNext()) {
                return true;
            }
            pilha.pop();
        }
        return false;
    }

    @Override
    public ComponenteCatalogo next() {
        if (!hasNext()) {
            throw new NoSuchElementException("A árvore já foi toda percorrida.");
        }
        ComponenteCatalogo atual = pilha.peek().next();
        // Empilha os filhos diretos do item atual (folhas empilham um NullIterator).
        pilha.push(atual.criarIterator());
        return atual;
    }
}
