package com.mediasoft.airport.generators;

import com.mediasoft.airport.Airport;
import com.mediasoft.airport.entity.Airplane;
import com.mediasoft.airport.entity.Airports;

import java.util.Random;

public class AirplaneGenerator implements Runnable {

    private final Random random = new Random();
    private final Airport airport;
    private final int airplanesCount;

    public AirplaneGenerator(Airport airport, int airplanesCount) {
        this.airport = airport;
        this.airplanesCount = airplanesCount;
    }

    @Override
    public void run() {
        int count = 0;
        while (count < airplanesCount) {
            Thread.currentThread().setName("Airplane generator");
            count++;
            airport.land(
                    new Airplane(
                            getRandomCapacity(),
                            getRandomCapacity(),
                            getRandomCapacity() * 3,
                            getRandomAirport()
                    )
            );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    private int getRandomCapacity() {
        return random.nextInt(10) * 10;
    }

    private Airports getRandomAirport() {
        return Airports.values()[random.nextInt(Airports.values().length)];
    }
}
