package com.mediasoft.airport.entity;

import com.mediasoft.airport.exception.AirplaneLoadingException;

public class Airplane {
    private final int perishableCargoCapacity; // вместимость скоропортящийся
    private final int liveCargoCapacity; // вместимость живой
    private final int regularCargoCapacity; // вместимость обычный и опасный
    private final Airports targetAirport;
    private final int tailNumber;

    private volatile boolean isLoadingInProgress = false;
    private volatile boolean isLoaded = false;

    private int perishableCargoCount = 0;
    private int liveCargoCount = 0;
    private int regularCargoCount = 0;

    public Airplane(
            int perishableCargoCapacity,
            int liveCargoCapacity,
            int regularCargoCapacity,
            Airports targetAirport,
            int tailNumber
    ) {
        this.perishableCargoCapacity = perishableCargoCapacity;
        this.liveCargoCapacity = liveCargoCapacity;
        this.regularCargoCapacity = regularCargoCapacity;
        this.targetAirport = targetAirport;
        this.tailNumber = tailNumber;
    }

    public void loadCargo(Cargo cargo, CargoType loadType) throws AirplaneLoadingException {
        if (!isLoadingInProgress || isLoaded) {
            throw new AirplaneLoadingException("Airplane is not ready to load cargo: loading has not been started or completed yet!");
        }
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

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoadingStart() throws AirplaneLoadingException {
        if (!isLoaded) {
            isLoadingInProgress = true;
        } else {
            throw new AirplaneLoadingException("Airplane is not ready to load cargo: loading completed yet!");
        }
    }

    public void setLoadingFinish() throws AirplaneLoadingException {
        if (isLoadingInProgress) {
            isLoadingInProgress = false;
            isLoaded = true;
        } else {
            throw new AirplaneLoadingException("Airplane is not ready to complete loading cargo: loading has not been started!");
        }
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
                "tailNumber=" + tailNumber +
                ", perishableCargoCapacity=" + perishableCargoCapacity +
                ", perishableCargoCount=" + perishableCargoCount +
                ", liveCargoCapacity=" + liveCargoCapacity +
                ", liveCargoCount=" + liveCargoCount +
                ", regularCargoCapacity=" + regularCargoCapacity +
                ", regularCargoCount=" + regularCargoCount +
                ", targetAirport=" + targetAirport +
                '}';
    }
}
