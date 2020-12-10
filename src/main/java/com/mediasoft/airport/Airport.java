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

    public synchronized void land(Airplane airplane) {
        try {
            if(AirplanesCounter < maxAirplanesInPort) {
                notifyAll();
                landed.add(airplane);
                logger.info("Landed airplane: " + airplane.toString());
                AirplanesCounter++;
            } else {
                logger.info("No place to land airplane: " + airplane.toString());
                wait();
            }
        } catch (InterruptedException interruptedException) {
            logger.error("Exception in com.mediasoft.airport.Airport: [{}]", interruptedException.getMessage(), interruptedException);
        }
    }

    public synchronized Airplane getLoaded() {
        try {
            if (AirplanesCounter > minAirplanesInPort) {
                notifyAll();
                for (Airplane airplane : landed) {
                    if (airplane.isLoaded()) {
                        logger.info("Airplane " + airplane.toString() + " IS READY TO TAKEOFF!");
                        return airplane;
                    }
                }
            } else {
                logger.info("There is no airplanes in the airport!");
                wait();
            }
        } catch (InterruptedException interruptedException) {
            logger.error("Exception in com.mediasoft.airport.Airport: [{}]", interruptedException.getMessage(), interruptedException);
        }
        return null;
    }

    public synchronized Airplane getReadyToLoad() {
        try {
            if (AirplanesCounter > minAirplanesInPort) {
                notifyAll();
                for (Airplane airplane : landed) {
                    if (!airplane.isLoaded() && !airplane.isLoadingInProgress()) {
                        logger.info("Airplane " + airplane.toString() + " IS READY TO LOAD!");
                        return airplane;
                    }
                }
            } else {
                logger.info("There is no airplanes in the airport!");
                wait();
            }
        } catch (InterruptedException interruptedException) {
            logger.error("Exception in com.mediasoft.airport.Airport: [{}]", interruptedException.getMessage(), interruptedException);
        }
        return null;
    }

    public synchronized void takeoff(Airplane airplane) {
        try {
            if (AirplanesCounter > minAirplanesInPort) {
                notifyAll();
                logger.info("Takeoff airplane " + airplane.toString());
                AirplanesCounter--;
                landed.remove(airplane);
            } else {
                logger.info("There is no airplanes in the airport!");
                wait();
            }
        } catch (InterruptedException interruptedException) {
            logger.error("Exception in com.mediasoft.airport.Airport: [{}]", interruptedException.getMessage(), interruptedException);
        }
    }
}
