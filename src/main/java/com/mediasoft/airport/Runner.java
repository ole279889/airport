package com.mediasoft.airport;

import com.mediasoft.airport.generators.AirplaneGenerator;
import com.mediasoft.airport.generators.CargoGenerator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {
    public static void main(String[] args) {
        Airport airport = new Airport();
        CargoStock cargoStock = new CargoStock();
        AirplaneGenerator airplaneGenerator = new AirplaneGenerator(airport, 20);
        CargoGenerator cargoGenerator = new CargoGenerator(cargoStock, 500);
        AirplaneDisplacer airplaneDisplacer = new AirplaneDisplacer(airport);
        CargoLoader cargoLoaderFirst = new CargoLoader(airport, cargoStock);
        CargoLoader cargoLoaderSecond = new CargoLoader(airport, cargoStock);

        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(cargoGenerator);
        service.execute(airplaneGenerator);
        service.execute(airplaneDisplacer);
        service.execute(cargoLoaderFirst);
        service.execute(cargoLoaderSecond);

        service.shutdown();
    }
}
