package techlearn.composite;

import techlearn.iterator.ArrayModuloIterator;
import techlearn.iterator.CompositeIterator;
import techlearn.iterator.Iterator;
import techlearn.logging.LoggerService;
import java.util.Arrays;

// Módulo de um sistema antigo que guarda os filhos em array, não em List.
// Para o cliente não faz diferença: ele só enxerga o Iterator.
public class ModuloLegado extends ComponenteCatalogo {
    private static final int CAPACIDADE_INICIAL = 2;

    private String nome;
    private ComponenteCatalogo[] subComponentes = new ComponenteCatalogo[CAPACIDADE_INICIAL];
    private int quantidade = 0;

    public ModuloLegado(String nome) {
        this.nome = nome;
    }

    @Override
    public void adicionar(ComponenteCatalogo componente) {
        if (quantidade == subComponentes.length) {
            // Array cheio: dobra a capacidade.
            subComponentes = Arrays.copyOf(subComponentes, subComponentes.length * 2);
            LoggerService.getInstance().log("Capacidade do módulo '" + this.nome
                    + "' aumentada para " + subComponentes.length + ".");
        }
        subComponentes[quantidade++] = componente;
        LoggerService.getInstance().log("Item '" + componente.getNome() + "' adicionado ao módulo '" + this.nome + "'.");
    }

    @Override
    public void remover(ComponenteCatalogo componente) {
        for (int i = 0; i < quantidade; i++) {
            if (subComponentes[i].equals(componente)) {
                // Puxa os itens seguintes uma posição para trás.
                System.arraycopy(subComponentes, i + 1, subComponentes, i, quantidade - i - 1);
                subComponentes[--quantidade] = null;
                LoggerService.getInstance().log("Item '" + componente.getNome() + "' removido do módulo '" + this.nome + "'.");
                return;
            }
        }
        LoggerService.getInstance().log("Item '" + componente.getNome() + "' não encontrado no módulo '" + this.nome + "'.");
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getDuracaoMinutos() {
        int total = 0;
        Iterator<ComponenteCatalogo> it = criarIterator();
        while (it.hasNext()) {
            total += it.next().getDuracaoMinutos();
        }
        return total;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("\n[Módulo/Curso]: " + getNome() + " | Duração Total: " + getDuracaoMinutos() + " min");
        System.out.println("--------------------------------------------------");
        Iterator<ComponenteCatalogo> it = criarIterator();
        while (it.hasNext()) {
            it.next().exibirDetalhes();
        }
    }

    @Override
    public Iterator<ComponenteCatalogo> criarIterator() {
        return new ArrayModuloIterator(subComponentes, quantidade);
    }

    @Override
    public Iterator<ComponenteCatalogo> criarIteratorCompleto() {
        return new CompositeIterator(criarIterator());
    }
}
