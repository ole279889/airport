package com.mediasoft.airport;

import com.mediasoft.airport.entity.Airplane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirplaneDisplacer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(AirplaneDisplacer.class);

    private final Airport airport;

    public AirplaneDisplacer(Airport airport) {
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
                logger.error("Exception in AirplaneDisplacer: [{}]", interruptedException.getMessage(), interruptedException);
            }
        }
    }
}
