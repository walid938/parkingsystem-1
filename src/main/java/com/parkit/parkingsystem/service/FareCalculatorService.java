package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;


/**
 *this class calculates the price to be paid by the user.
 * while exiting the parking.
 */
public class FareCalculatorService {
    /**
     * number of milliseconds per minute.
     * used to get convert the parking duration
     * from milliseconds to minutes
     */
    private static final int MILLISECONDS_PER_MINUTE = 60000;

    /**
     * number of minutes per hour.
     */
    private static final double MINUTES_PER_HOUR = 60;

    /**
     *number of minutes bellow which.
     * the free parking service is applied
     */
    private static final int FREE_SERVICE = 30;

    /**
     * One hundred percent.
     * it represents the percentage that will be paid
     * by all new users users
     */
    private static final int FARE_TO_BE_PAID_BY_NEW_USERS = 100;

    /**
     * 95%: it represents the percentage.
     * that will be paid by recurring users
     */
    private static final int FARE_TO_BE_PAID_BY_RECURRING_USERS = 95;

    /**
     * a method that checks whether the exit time is correct.
     * it throw exception if the exit time is null or comes before incoming time
     * @param ticket the ticket to which we calculate fare
     */
    public void errorWhilePrecessingExitingVehicle(final Ticket ticket) {
        if ((ticket.getOutTime() == null)
                || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException(
                    "Out time provided is incorrect: "
                            + ticket.getOutTime().toString());
        }
    }

    /**
     * a method that calculates the time spent in the parking by a user.
     * @param ticket the ticket to which we calculate fare
     * @return the time spent in the parking in minutes
     */
    public long calculateParkingDuration(final Ticket ticket) {
        //incoming hour
        long inHour = ticket.getInTime().getTime();
        //leaving hour
        long outHour = ticket.getOutTime().getTime();
        //converting parking duration from milliseconds to minutes
        long result = ((outHour - inHour) / MILLISECONDS_PER_MINUTE);
        return result ;
        
       

    }

    /**
     * this method permits the free 30 minutes parking service.
     * @param ticket the ticket to which we calculate fare
     */
    private boolean isFreeParkingService(final Ticket ticket) {
        errorWhilePrecessingExitingVehicle(ticket);
        if (calculateParkingDuration(ticket) <= FREE_SERVICE) {
            return true;
        }
        return false;
    }

    /**
     * a method that calculates the price to be paid by the user.
     * @param ticket the ticket to which we calculate fare
     */
    public void calculateFare(final Ticket ticket) {

        errorWhilePrecessingExitingVehicle(ticket);
        if (isFreeParkingService(ticket)) {
        	
        	ticket.setPrice(0);
        }
        else {
        	switch (ticket.getParkingSpot().getParkingType()) {

            case CAR:
                ticket.setPrice(calculateParkingDuration(ticket)
                        * (Fare.CAR_RATE_PER_HOUR / MINUTES_PER_HOUR));
                break;

            case BIKE:
                ticket.setPrice(calculateParkingDuration(ticket)
                        * (Fare.BIKE_RATE_PER_HOUR / MINUTES_PER_HOUR));
                break;

            default: throw new IllegalArgumentException("Unknown Parking Type");
        }
        
        }
        
    }

    /**
     * a method that calculates the price to be paid by the recurring users.
     * @param ticket the ticket to which we calculate fare
     */
    public void calculateFareForRegularUsers(final Ticket ticket) {

        errorWhilePrecessingExitingVehicle(ticket);

       
        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR:
                ticket.setPrice(((calculateParkingDuration(ticket)
                        * (Fare.CAR_RATE_PER_HOUR / MINUTES_PER_HOUR))
                        * FARE_TO_BE_PAID_BY_RECURRING_USERS)
                        / FARE_TO_BE_PAID_BY_NEW_USERS);
               
                break;
            case BIKE:
                ticket.setPrice(((calculateParkingDuration(ticket)
                        * (Fare.BIKE_RATE_PER_HOUR / MINUTES_PER_HOUR))
                        * FARE_TO_BE_PAID_BY_RECURRING_USERS)
                        / FARE_TO_BE_PAID_BY_NEW_USERS);
                
                break;
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }
    }
}
