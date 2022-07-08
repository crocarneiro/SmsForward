package br.com.smsforward;

import java.time.ZoneId;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    public static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");
    private static ExecutorService executorService;

    public static ExecutorService getExecutorService() {
        if(executorService == null) {
            executorService = Executors.newFixedThreadPool(2);
        }

        return executorService;
    }
}
