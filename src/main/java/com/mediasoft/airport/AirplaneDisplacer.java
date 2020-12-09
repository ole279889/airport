package com.mediasoft.airport;

import com.mediasoft.airport.entity.Airplane;

public class AirplaneDisplacer implements Runnable {
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
                interruptedException.printStackTrace();
            }
        }
    }
}
