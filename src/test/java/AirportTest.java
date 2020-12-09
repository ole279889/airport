import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mediasoft.airport.Airport;
import com.mediasoft.airport.CargoStock;
import com.mediasoft.airport.generators.AirplaneGenerator;
import com.mediasoft.airport.generators.CargoGenerator;
import com.mediasoft.airport.AirplaneDisplacer;
import com.mediasoft.airport.CargoLoader;

import org.junit.jupiter.api.Test;

public class AirportTest {
    @Test
    public void runApp() {
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
