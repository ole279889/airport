package com.mediasoft.airport;

import com.mediasoft.airport.entity.Airplane;
import com.mediasoft.airport.entity.Cargo;
import com.mediasoft.airport.entity.CargoType;
import com.mediasoft.airport.exception.AirplaneLoadingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CargoLoader implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CargoLoader.class);

    private final CargoStock cargoStock;
    private final Airport airport;

    public CargoLoader(Airport airport, CargoStock cargoStock) {
        this.airport = airport;
        this.cargoStock = cargoStock;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(300);
                Airplane airplane = airport.getReadyToLoad();
                if (airplane != null) {
                    airplane.setLoadingStart();
                    loadLiveCargo(airplane);
                    loadPerishableCargo(airplane);
                    loadDangerousCargo(airplane);
                    loadRegularCargo(airplane);
                    airplane.setLoadingFinish();
                    logger.info("Loaded airplane " + airplane.toString());
                }
            } catch (AirplaneLoadingException airplaneLoadingException) {
                logger.error("Exception in CargoLoader: [{}]", airplaneLoadingException.getMessage(), airplaneLoadingException);
            } catch (InterruptedException interruptedException) {
                logger.error("Exception in CargoLoader: [{}]", interruptedException.getMessage(), interruptedException);
            }
        }
    }

    private void loadLiveCargo(Airplane airplane) throws InterruptedException, AirplaneLoadingException {
        Cargo cargo = cargoStock.getCargo(CargoType.LIVE, airplane.getTargetAirport(), airplane.getLiveCargoFreeSpace());
        cargoLoaderLoggerReport(airplane.getLiveCargoFreeSpace(), cargo);
        while (cargo != null) {
            Thread.sleep(100);
            airplane.loadCargo(cargo, CargoType.LIVE);
            cargo = cargoStock.getCargo(CargoType.LIVE, airplane.getTargetAirport(), airplane.getLiveCargoFreeSpace());
            cargoLoaderLoggerReport(airplane.getLiveCargoFreeSpace(), cargo);
        }
    }

    private void loadPerishableCargo(Airplane airplane) throws InterruptedException, AirplaneLoadingException {
        Cargo cargo = cargoStock.getCargo(CargoType.PERISHABLE, airplane.getTargetAirport(), airplane.getPerishableCargoFreeSpace());
        cargoLoaderLoggerReport(airplane.getPerishableCargoFreeSpace(), cargo);
        while (cargo != null) {
            Thread.sleep(100);
            airplane.loadCargo(cargo, CargoType.PERISHABLE);
            cargo = cargoStock.getCargo(CargoType.PERISHABLE, airplane.getTargetAirport(), airplane.getPerishableCargoFreeSpace());
            cargoLoaderLoggerReport(airplane.getPerishableCargoFreeSpace(), cargo);
        }
    }

    private void loadDangerousCargo(Airplane airplane) throws InterruptedException, AirplaneLoadingException {
        Cargo cargo = cargoStock.getCargo(CargoType.DANGEROUS, airplane.getTargetAirport(), airplane.getRegularCargoFreeSpace());
        cargoLoaderLoggerReport(airplane.getRegularCargoFreeSpace(), cargo);
        while (cargo != null) {
            Thread.sleep(100);
            airplane.loadCargo(cargo, CargoType.DANGEROUS);
            cargo = cargoStock.getCargo(CargoType.DANGEROUS, airplane.getTargetAirport(), airplane.getRegularCargoFreeSpace());
            cargoLoaderLoggerReport(airplane.getRegularCargoFreeSpace(), cargo);
        }
    }

    private void loadRegularCargo(Airplane airplane) throws InterruptedException, AirplaneLoadingException {
        Cargo cargo = cargoStock.getCargo(CargoType.REGULAR, airplane.getTargetAirport(), airplane.getRegularCargoFreeSpace());
        cargoLoaderLoggerReport(airplane.getRegularCargoFreeSpace(), cargo);
        while (cargo != null) {
            Thread.sleep(100);
            airplane.loadCargo(cargo, CargoType.REGULAR);
            cargo = cargoStock.getCargo(CargoType.REGULAR, airplane.getTargetAirport(), airplane.getRegularCargoFreeSpace());
            cargoLoaderLoggerReport(airplane.getRegularCargoFreeSpace(), cargo);
        }
    }

    private void cargoLoaderLoggerReport(int freeSpace, Cargo cargo) {
        logger.info("free space: " + freeSpace);
        logger.info(cargo == null ? "No cargo found" : "Adding cargo: " + cargo.toString());
    }

}
