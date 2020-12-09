package com.mediasoft.airport;

import com.mediasoft.airport.entity.Airplane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Airport {
    private static final Logger logger = LoggerFactory.getLogger(Airport.class);

    private List<Airplane> landed;
    private static final int maxAirplanesInPort = 10;
    private static final int minAirplanesInPort = 0;
    private int AirplanesCounter = 0;

    public Airport() {
        this.landed = new ArrayList<>();
    }

    public synchronized boolean land(Airplane airplane) {
        try {
            if(AirplanesCounter < maxAirplanesInPort) {
                notifyAll();
                landed.add(airplane);
                logger.debug("Landed airplane: " + airplane.toString());
                AirplanesCounter++;
            } else {
                logger.debug("No place to land airplane: " + airplane.toString());
                wait();
                return false;
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        return true;
    }

    public synchronized Airplane getLoaded() {
        try {
            if (AirplanesCounter > minAirplanesInPort) {
                notifyAll();
                for (Airplane airplane : landed) {
                    if (airplane.isLoaded()) {
                        logger.debug("Airplane " + airplane.toString() + " IS READY TO TAKEOFF!");
                        return airplane;
                    }
                }
            } else {
                logger.debug("There is no airplanes in the airport!");
                wait();
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        return null;
    }

    public synchronized Airplane getReadyToLoad() {
        try {
            if (AirplanesCounter > minAirplanesInPort) {
                notifyAll();
                for (Airplane airplane : landed) {
                    if (!airplane.isLoaded() && !airplane.isLoadingInProgress()) {
                        logger.debug("Airplane " + airplane.toString() + " IS READY TO LOAD!");
                        return airplane;
                    }
                }
            } else {
                logger.debug("There is no airplanes in the airport!");
                wait();
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        return null;
    }

    public synchronized void takeoff(Airplane airplane) {
        logger.debug("Takeoff airplane " + airplane.toString());
        AirplanesCounter--;
        landed.remove(airplane);
    }
}
