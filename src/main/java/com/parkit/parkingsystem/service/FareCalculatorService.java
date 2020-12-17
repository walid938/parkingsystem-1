package com.parkit.parkingsystem.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket, boolean recurringUser) {
		if ((ticket.getOutTime() == null) || ticket.getOutTime().isBefore(ticket.getInTime())) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		LocalDateTime inHour = ticket.getInTime();
		LocalDateTime outHour = ticket.getOutTime();

		long diffInOut = ChronoUnit.MINUTES.between(inHour, outHour); 	// Calcul de la différence entre heure d'entrée et
																		// de sortie avec ChronoUnit en minutes
		double duration = ((double) diffInOut / 60); 					// Conversion en heure

		if (duration <= Fare.FREE_HOURS) { 								// 30 premières minutes gratuites
			ticket.setPrice(Fare.FREE_PRICE);
		} else {
			double factor = recurringUser ? Fare.DISCOUNT_RATE : 1.0;

			switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR * factor);
				break;
			}
			case BIKE: {
				ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR * factor);
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
			}
		}
	}

}