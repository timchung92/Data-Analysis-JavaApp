package edu.upenn.cit594.data;

import java.util.Date;

/**
 * A class for parking violations which includes their date and time, fine in dollars, description,
 * identifying information about (vehicle ID + state), a violation ID, and the zipcode of where the violation
 * occurred
 *
 * @author Chris Henry + Tim Chung
 */
public class ParkingViolation {
    protected Date timeStamp;
    protected int zipCode, id;
    protected double fineInDollars;
    protected String description, vehicleId, vehicleState;

    /**
     * @param timeStamp     date and time of violation occurrence
     * @param fineInDollars violation fine in dollars
     * @param description   description of violation
     * @param vehicleId     license plate of vehicle in violation
     * @param vehicleState  state of license plate of vehicle in violation
     * @param id            unique identifier for the violation
     * @param zipCode       zip code where the violation occurred
     */
    public ParkingViolation(Date timeStamp, double fineInDollars, String description, String vehicleId,
                            String vehicleState, int id, int zipCode) {
        this.timeStamp = timeStamp;
        this.fineInDollars = fineInDollars;
        this.description = description;
        this.vehicleId = vehicleId;
        this.vehicleState = vehicleState;
        this.id = id;
        this.zipCode = zipCode;
    }

    /**
     * @return timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * @return fineInDollars
     */
    public double getFineInDollars() {
        return fineInDollars;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return vehicleId
     */
    public String getVehicleId() {
        return vehicleId;
    }

    /**
     * @return vehicleState
     */
    public String getVehicleState() {
        return vehicleState;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @return zipCode
     */
    public int getZipCode() {
        return zipCode;
    }
}
