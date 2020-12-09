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
        CargoLoader cargoLoader = new CargoLoader(airport, cargoStock);
        //CargoLoader cargoLoader2 = new CargoLoader(airport, cargoStock);

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        service.execute(cargoGenerator);
        service.execute(airplaneGenerator);
        service.execute(airplaneDisplacer);
        service.execute(cargoLoader);
        //service.execute(cargoLoader2);

        service.shutdown();
    }
}
