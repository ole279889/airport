package com.mediasoft.airport;

import com.mediasoft.airport.entity.Airplane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirplaneDispatcher implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(AirplaneDispatcher.class);

    private final Airport airport;

    public AirplaneDispatcher(Airport airport) {
        this.airport = airport;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(300);
                Airplane airplane = airport.getLoaded();
                if (airplane != null) {
                    airport.takeoff(airplane);
                }
            } catch (InterruptedException interruptedException) {
                logger.error("Exception in AirplaneDispatcher: [{}]", interruptedException.getMessage(), interruptedException);
            }
        }
    }
}
