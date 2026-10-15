package techlearn;

import techlearn.composite.*;
import techlearn.iterator.Iterator;
import techlearn.logging.LoggerService;

public class Main {
    public static void main(String[] args) {
        LoggerService logger = LoggerService.getInstance();
        logger.log("Iniciando montagem da estrutura de cursos...");

        Modulo cursoEngenharia = new Modulo("Engenharia de Software Moderna");

        Modulo moduloOO = new Modulo("Módulo 1: Orientação a Objetos Avançada");
        moduloOO.adicionar(new Aula("Aula 1.1: Coesão e Acoplamento", 45));
        moduloOO.adicionar(new Aula("Aula 1.2: Interfaces e Polimorfismo", 60));

        Modulo moduloPadroes = new Modulo("Módulo 2: Padrões de Projeto (GoF)");
        moduloPadroes.adicionar(new Aula("Aula 2.1: Singleton na Prática", 30));
        moduloPadroes.adicionar(new Aula("Aula 2.2: Composite Hierárquico", 50));
        moduloPadroes.adicionar(new Aula("Aula 2.3: Iterator para Coleções", 40));

        // Submódulo dentro do Módulo 2, guardado em array (ModuloLegado).
        ModuloLegado moduloComportamentais = new ModuloLegado("Módulo 2.4: Padrões Comportamentais (legado)");
        moduloComportamentais.adicionar(new Aula("Aula 2.4.1: Observer e Eventos", 35));
        moduloComportamentais.adicionar(new Aula("Aula 2.4.2: Strategy no Dia a Dia", 55));
        moduloComportamentais.adicionar(new Aula("Aula 2.4.3: Template Method", 25));
        moduloPadroes.adicionar(moduloComportamentais);

        cursoEngenharia.adicionar(moduloOO);
        cursoEngenharia.adicionar(moduloPadroes);

        logger.log("Estrutura montada com sucesso. Exibindo detalhes da ementa:");
        cursoEngenharia.exibirDetalhes();

        System.out.println("\n==================================================");
        System.out.println("Varredura do Curso Principal via Iterator:");
        System.out.println("==================================================");

        Iterator<ComponenteCatalogo> iterator = cursoEngenharia.criarIterator();
        while (iterator.hasNext()) {
            ComponenteCatalogo subItem = iterator.next();
            logger.log("Navegando no Item Raiz: " + subItem.getNome()
                    + " | Duração acumulada: " + subItem.getDuracaoMinutos() + " min");
        }

        System.out.println("\n==================================================");
        System.out.println("Varredura Completa da Árvore via CompositeIterator:");
        System.out.println("==================================================");

        Iterator<ComponenteCatalogo> completo = cursoEngenharia.criarIteratorCompleto();
        while (completo.hasNext()) {
            ComponenteCatalogo item = completo.next();
            String tipo = item.ehFolha() ? "Aula  " : "Módulo";
            logger.log("[" + tipo + "] " + item.getNome() + " (" + item.getDuracaoMinutos() + " min)");
        }

        // Passo 5: Desafio de Extensão Prática
        System.out.println("\n==================================================");
        System.out.println("Desafio Passo 5: Filtro de Conteúdo Curto (<= 45 min):");
        System.out.println("==================================================");
        logger.log("Iniciando filtragem de aulas individuais com duração <= 45 min...");
        filtrarAulasCurtas(cursoEngenharia, 45);

        System.out.println("\n==================================================");
        System.out.println("Teste do remover:");
        System.out.println("==================================================");
        Aula aulaExtra = new Aula("Aula Extra: Revisão Geral", 20);
        moduloOO.remover(aulaExtra);
        moduloOO.adicionar(aulaExtra);
        moduloOO.remover(aulaExtra);

        logger.log("Execução da atividade finalizada.");
    }

    // Passo 5: o CompositeIterator entrega todos os itens da árvore, aqui só filtramos.
    public static void filtrarAulasCurtas(ComponenteCatalogo componente, int limiteMinutos) {
        Iterator<ComponenteCatalogo> iterator = componente.criarIteratorCompleto();

        while (iterator.hasNext()) {
            ComponenteCatalogo item = iterator.next();
            if (item.ehFolha() && item.getDuracaoMinutos() <= limiteMinutos) {
                LoggerService.getInstance().log("[FILTRO <= " + limiteMinutos + " min] "
                        + item.getNome() + " (" + item.getDuracaoMinutos() + " min)");
            }
        }
    }
}
