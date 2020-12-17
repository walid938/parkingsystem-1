package com.parkit.parkingsystem;

import java.time.LocalDateTime;
import java.time.Month;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class FareCalculatorServiceTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    @Test
    public void calculateFareCar(){
        LocalDateTime inTime = LocalDateTime.of(2019, Month.DECEMBER,01,10,05,32);      // Remplacement de la méthode Date.setTime() par LocalDateTime.of()
        // inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        LocalDateTime outTime = LocalDateTime.of(2019,Month.DECEMBER,01,11,05,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
    }

    @Test
    public void calculateFareBike(){
        LocalDateTime inTime = LocalDateTime.of(2019, Month.DECEMBER,01,10,05,32);
        //inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        LocalDateTime outTime = LocalDateTime.of(2019, Month.DECEMBER,01,11,05,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);
    }

    @Test
    @Tag("TestsException")
    public void calculateFareUnkownType(){
        LocalDateTime inTime = LocalDateTime.of(2019,Month.DECEMBER,01,10,05,32);
        //inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        LocalDateTime outTime = LocalDateTime.of(2019,Month.DECEMBER,01,11,05,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket, false));
    }

    @Test
    @Tag("TestsException")
    public void calculateFareBikeWithFutureInTime(){
        LocalDateTime inTime = LocalDateTime.of(2019,Month.DECEMBER,01,18,05,32);
        //inTime.setTime( System.currentTimeMillis() + (  60 * 60 * 1000) );
        LocalDateTime outTime = LocalDateTime.of(2019,Month.DECEMBER,01,17,05,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket, false));
    }

    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTime(){
        LocalDateTime inTime = LocalDateTime.of(2019,Month.DECEMBER,01,18,05,32);
        //inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );
        LocalDateTime outTime = LocalDateTime.of(2019,Month.DECEMBER,01,18,50,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareCarWithLessThanOneHourParkingTime(){
        LocalDateTime inTime = LocalDateTime.of(2019,Month.DECEMBER,01,18,05,32);
        //inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );			
        LocalDateTime outTime = LocalDateTime.of(2019,Month.DECEMBER,01,18,50,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals( (0.75 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    public void calculateFareCarWithMoreThanADayParkingTime(){
        LocalDateTime inTime = LocalDateTime.of(2019,Month.DECEMBER,01,18,45,32);
        //inTime.setTime( System.currentTimeMillis() - (  24 * 60 * 60 * 1000) );		
        LocalDateTime outTime = LocalDateTime.of(2019,Month.DECEMBER,02,18,45,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals( (24 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }
    @Test
    @Tag("TrentePrenièresMinutesGratuites")
    public void calculateFareBikeWithLFirstThirtyMinutesFree(){
        LocalDateTime inTime = LocalDateTime.of(2019,Month.DECEMBER,01,18,05,32);
        LocalDateTime outTime = LocalDateTime.of(2019,Month.DECEMBER,01,18,35,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals(Fare.FREE_PRICE, ticket.getPrice() );
    }
    @Test
    @Tag("TrentePremièresMinutesGratuites")
    public void calculateFareCarWithLFirstThirtyMinutesFree(){
        LocalDateTime inTime = LocalDateTime.of(2019,Month.DECEMBER,01,18,05,32);
        LocalDateTime outTime = LocalDateTime.of(2019,Month.DECEMBER,01,18,35,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals(Fare.FREE_PRICE, ticket.getPrice() );
    }
    @Test
    @Tag("RabaisUtilisateursRécurrents")
    public void calculateFareCarWithReccurentUser() {
    	LocalDateTime inTime = LocalDateTime.of(2019,Month.DECEMBER,01,18,05,32);
        LocalDateTime outTime = LocalDateTime.of(2019,Month.DECEMBER,01,19,05,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, true);
        assertEquals( Fare.CAR_RATE_PER_HOUR * Fare.DISCOUNT_RATE,ticket.getPrice());
    }
    @Test
    @Tag("RabaisUtilisateursRécurrents")
    public void calculateFareBikeWithRecurrentUser() {
    	LocalDateTime inTime = LocalDateTime.of(2019,Month.DECEMBER,01,18,05,32);
        LocalDateTime outTime = LocalDateTime.of(2019,Month.DECEMBER,01,19,05,32);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, true);
        assertEquals( Fare.BIKE_RATE_PER_HOUR *Fare.DISCOUNT_RATE,ticket.getPrice());
    }
}