package techlearn;

import techlearn.logging.LoggerService;
import java.util.concurrent.CountDownLatch;

// Sobe várias threads pedindo o LoggerService ao mesmo tempo e confere se todas
// receberam o mesmo objeto.
public class TesteSingletonThreads {
    private static final int TOTAL_THREADS = 20;

    public static void main(String[] args) throws InterruptedException {
        final LoggerService[] referencias = new LoggerService[TOTAL_THREADS];
        final CountDownLatch largada = new CountDownLatch(1);
        Thread[] threads = new Thread[TOTAL_THREADS];

        for (int i = 0; i < TOTAL_THREADS; i++) {
            final int indice = i;
            threads[i] = new Thread(() -> {
                try {
                    largada.await(); // todas esperam aqui e saem juntas
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                referencias[indice] = LoggerService.getInstance();
            });
            threads[i].start();
        }

        System.out.println("Disparando " + TOTAL_THREADS + " threads ao mesmo tempo...");
        largada.countDown();
        for (Thread t : threads) {
            t.join();
        }

        boolean todasIguais = true;
        for (LoggerService ref : referencias) {
            if (ref == null || ref != referencias[0]) {
                todasIguais = false;
                break;
            }
        }

        System.out.println("Referências coletadas: " + referencias.length);
        System.out.println("Identidade da primeira instância: " + System.identityHashCode(referencias[0]));
        System.out.println("Todas as threads receberam o mesmo objeto? " + (todasIguais ? "SIM" : "NÃO"));
    }
}
