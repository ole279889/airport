package com.mediasoft.airport.entity;

public class Airplane {
    private final int perishableCargoCapacity; // вместимость скоропортящийся
    private final int liveCargoCapacity; // вместимость живой
    private final int regularCargoCapacity; // вместимость обычный и опасный
    private final Airports targetAirport;

    private volatile boolean isLoadingInProgress = false;
    private volatile boolean isLoaded = false;

    private int perishableCargoCount = 0;
    private int liveCargoCount = 0;
    private int regularCargoCount = 0;

    public Airplane(
            int perishableCargoCapacity,
            int liveCargoCapacity,
            int regularCargoCapacity,
            Airports targetAirport
    ) {
        this.perishableCargoCapacity = perishableCargoCapacity;
        this.liveCargoCapacity = liveCargoCapacity;
        this.regularCargoCapacity = regularCargoCapacity;
        this.targetAirport = targetAirport;
    }

    public void loadCargo(Cargo cargo, CargoType loadType) {
        switch (loadType) {
            case LIVE:
                this.liveCargoCount += cargo.getSize();
                break;
            case DANGEROUS:
                this.regularCargoCount += cargo.getSize();
                break;
            case PERISHABLE:
                this.perishableCargoCount += cargo.getSize();
                break;
            case REGULAR:
                this.regularCargoCount += cargo.getSize();
                break;
        }
    }

    public boolean isLoadingInProgress() {
        return isLoadingInProgress;
    }

    public void setLoadingInProgress(boolean loadingInProgress) {
        isLoadingInProgress = loadingInProgress;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded() {
        isLoaded = true;
    }

    public Airports getTargetAirport() {
        return targetAirport;
    }

    public int getRegularCargoFreeSpace() {
        return regularCargoCapacity - regularCargoCount;
    }

    public int getPerishableCargoFreeSpace() {
        return perishableCargoCapacity - perishableCargoCount;
    }

    public int getLiveCargoFreeSpace() {
        return liveCargoCapacity - liveCargoCount;
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "perishableCargoCapacity=" + perishableCargoCapacity +
                ", perishableCargoCount=" + perishableCargoCount +
                ", liveCargoCapacity=" + liveCargoCapacity +
                ", liveCargoCount=" + liveCargoCount +
                ", regularCargoCapacity=" + regularCargoCapacity +
                ", regularCargoCount=" + regularCargoCount +
                ", targetAirport=" + targetAirport +
                '}';
    }
}
