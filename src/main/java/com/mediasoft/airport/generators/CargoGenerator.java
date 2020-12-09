package com.mediasoft.airport.generators;

import com.mediasoft.airport.CargoStock;
import com.mediasoft.airport.entity.Airports;
import com.mediasoft.airport.entity.Cargo;
import com.mediasoft.airport.entity.CargoType;

import java.util.Random;

public class CargoGenerator implements Runnable {

    private final Random random = new Random();
    private final CargoStock stock;
    private final int cargoCount;

    public CargoGenerator(CargoStock stock, int cargoCount) {
        this.stock = stock;
        this.cargoCount = cargoCount;
    }

    @Override
    public void run() {
        int count = 0;
        while (count < cargoCount) {
            Thread.currentThread().setName("Cargo generator");
            count++;
            stock.addCargo(new Cargo(getRandomCargoType(), getRandomCargoSize(), getRandomAirport()));
            try {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    private int getRandomCargoSize() {
        return random.nextInt(20) + 1;
    }

    private CargoType getRandomCargoType() {
        return CargoType.values()[random.nextInt(CargoType.values().length)];
    }

    private Airports getRandomAirport() {
        return Airports.values()[random.nextInt(Airports.values().length)];
    }
}
