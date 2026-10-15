package techlearn;

import techlearn.composite.Aula;
import techlearn.composite.ComponenteCatalogo;
import techlearn.composite.Modulo;
import techlearn.composite.ModuloLegado;
import techlearn.iterator.Iterator;
import techlearn.iterator.NullIterator;
import java.util.NoSuchElementException;

// Bateria de testes de conformidade com os contratos do Iterator e Composite.
public class TesteContratosAudit {
    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        // 1. Contrato do NullIterator (deve retornar false em hasNext e lançar exceção em next)
        NullIterator<String> nullIt = new NullIterator<>();
        if (!nullIt.hasNext()) passed++; else { System.err.println("NullIterator hasNext falhou"); failed++; }
        try {
            nullIt.next();
            System.err.println("NullIterator next() não lançou exceção"); failed++;
        } catch (NoSuchElementException e) {
            passed++;
        }

        // 2. Comportamento de folha da Aula
        Aula aula = new Aula("Aula Teste", 30);
        if (aula.ehFolha()) passed++; else { System.err.println("Aula ehFolha falhou"); failed++; }
        if (!aula.criarIterator().hasNext()) passed++; else { System.err.println("Aula criarIterator não vazio"); failed++; }
        if (!aula.criarIteratorCompleto().hasNext()) passed++; else { System.err.println("Aula criarIteratorCompleto não vazio"); failed++; }
        try {
            aula.adicionar(new Aula("SubAula", 10));
            System.err.println("Aula adicionar não lançou UnsupportedOperationException"); failed++;
        } catch (UnsupportedOperationException e) {
            passed++;
        }

        // 3. Contrato do ModuloIterator (módulo vazio e após esgotamento)
        Modulo mVazio = new Modulo("ModVazio");
        Iterator<ComponenteCatalogo> mItVazio = mVazio.criarIterator();
        if (!mItVazio.hasNext()) passed++; else { System.err.println("Modulo vazio hasNext falhou"); failed++; }
        try {
            mItVazio.next();
            System.err.println("Modulo vazio next() não lançou exceção"); failed++;
        } catch (NoSuchElementException e) {
            passed++;
        }

        Modulo mComItem = new Modulo("ModComItem");
        mComItem.adicionar(new Aula("AulaMod", 15));
        Iterator<ComponenteCatalogo> mItComItem = mComItem.criarIterator();
        if (mItComItem.hasNext() && mItComItem.next().getNome().equals("AulaMod")) passed++; else { System.err.println("ModuloIterator item falhou"); failed++; }
        if (!mItComItem.hasNext()) passed++; else { System.err.println("ModuloIterator hasNext após consumir falhou"); failed++; }
        try {
            mItComItem.next();
            System.err.println("ModuloIterator next() após esgotamento não lançou exceção"); failed++;
        } catch (NoSuchElementException e) {
            passed++;
        }

        // 4. ModuloLegado e ArrayModuloIterator (expansão, remoção no meio, recálculo e exaustão)
        ModuloLegado mLeg = new ModuloLegado("ModLegadoTeste");
        Aula a1 = new Aula("A1", 10);
        Aula a2 = new Aula("A2", 20);
        Aula a3 = new Aula("A3", 30);
        mLeg.adicionar(a1);
        mLeg.adicionar(a2);
        mLeg.adicionar(a3); // dispara expansão de capacidade (2 -> 4)
        if (mLeg.getDuracaoMinutos() == 60) passed++; else { System.err.println("mLeg duração falhou"); failed++; }

        mLeg.remover(new Aula("NaoExiste", 99)); // tentativa de remoção de item inexistente
        mLeg.remover(a2); // remoção de elemento intermediário
        if (mLeg.getDuracaoMinutos() == 40) passed++; else { System.err.println("mLeg duração após remover falhou"); failed++; }

        Iterator<ComponenteCatalogo> legIt = mLeg.criarIterator();
        ComponenteCatalogo item1 = legIt.next();
        ComponenteCatalogo item2 = legIt.next();
        if (item1.getNome().equals("A1") && item2.getNome().equals("A3")) passed++; else { System.err.println("Deslocamento do array falhou"); failed++; }
        if (!legIt.hasNext()) passed++; else { System.err.println("legIt hasNext deveria ser false"); failed++; }
        try {
            legIt.next();
            System.err.println("ArrayModuloIterator next() após esgotamento não lançou exceção"); failed++;
        } catch (NoSuchElementException e) {
            passed++;
        }

        // 5. CompositeIterator (percurso completo e NoSuchElementException após esgotamento)
        Iterator<ComponenteCatalogo> compIt = mLeg.criarIteratorCompleto();
        while (compIt.hasNext()) {
            compIt.next();
        }
        try {
            compIt.next();
            System.err.println("CompositeIterator next() após esgotamento não lançou exceção"); failed++;
        } catch (NoSuchElementException e) {
            passed++;
        }

        System.out.println("AUDIT CHECKS: " + passed + " PASSED, " + failed + " FAILED.");
    }
}
