package com.mediasoft.airport.entity;

public class Cargo {
    private final CargoType type;
    private final int size;
    private final Airports targetAirport;

    public Cargo(CargoType type, int size, Airports targetAirport) {
        this.type = type;
        this.size = size;
        this.targetAirport = targetAirport;
    }

    public CargoType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public Airports getTargetAirport() {
        return targetAirport;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "type=" + type +
                ", size=" + size +
                ", targetAirport=" + targetAirport +
                '}';
    }
}
