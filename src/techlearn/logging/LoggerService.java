package techlearn.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerService {
    private static final LoggerService INSTANCE = new LoggerService();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LoggerService() {
        System.out.println("[SISTEMA] Serviço de Logging Inicializado.");
    }

    public static LoggerService getInstance() {
        return INSTANCE;
    }

    public void log(String mensagem) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] [LOG]: " + mensagem);
    }
}
