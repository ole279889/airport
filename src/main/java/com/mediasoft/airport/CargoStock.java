package com.mediasoft.airport;

import com.mediasoft.airport.entity.Airports;
import com.mediasoft.airport.entity.Cargo;
import com.mediasoft.airport.entity.CargoType;

import java.util.ArrayList;
import java.util.List;

public class CargoStock {
    private List<Cargo> dangerousCargos;
    private List<Cargo> perishableCargos;
    private List<Cargo> liveCargos;
    private List<Cargo> regularCargos;


    public CargoStock() {
        this.dangerousCargos = new ArrayList<>();
        this.perishableCargos = new ArrayList<>();
        this.liveCargos = new ArrayList<>();
        this.regularCargos = new ArrayList<>();
    }

    public synchronized void addCargo(Cargo cargo) {
        switch (cargo.getType()) {
            case LIVE:
                liveCargos.add(cargo);
                break;
            case DANGEROUS:
                dangerousCargos.add(cargo);
                break;
            case PERISHABLE:
                perishableCargos.add(cargo);
                break;
            case REGULAR:
                regularCargos.add(cargo);
                break;
        }
    }

    public synchronized Cargo getCargo(CargoType preferredType, Airports airport, int maxSize) {
        List<Cargo> cargos = getCargosListByType(preferredType);
        Cargo cargo = findCargo(cargos, airport, maxSize);
        if (cargo == null && (preferredType == CargoType.LIVE || preferredType == CargoType.PERISHABLE)) {
            cargos = dangerousCargos;
            cargo = findCargo(cargos, airport, maxSize);
            if (cargo == null) {
                cargos = regularCargos;
                cargo = findCargo(cargos, airport, maxSize);
            }
        }
        return cargo;
    }

    private Cargo findCargo(List<Cargo> cargos, Airports airport, int maxSize) {
        for (Cargo cargo : cargos) {
            if (cargo.getTargetAirport() == airport && cargo.getSize() < maxSize) {
                cargos.remove(cargo);
                return cargo;
            }
        }
        return null;
    }

    private List<Cargo> getCargosListByType(CargoType type) {
        List<Cargo> cargos = null;
        switch (type) {
            case LIVE:
                cargos = liveCargos;
                break;
            case DANGEROUS:
                cargos = dangerousCargos;
                break;
            case PERISHABLE:
                cargos = perishableCargos;
                break;
            case REGULAR:
                cargos = regularCargos;
                break;
        }
        return cargos;
    }

}
