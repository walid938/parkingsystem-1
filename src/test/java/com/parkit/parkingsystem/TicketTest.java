package com.parkit.parkingsystem;

import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TicketTest {

	private Ticket ticket; 
	
	@Mock
	private ParkingSpot parkingSpot;

    @BeforeEach
    private void setUpPerTest() {
    	ticket = new Ticket();
    }

    @Test
    public void test_setAndGetID_equalConstantInt(){

    	//GIVEN
        
    	//WHEN
    	ticket.setId(3);
    	
    	//THEN
        assertEquals(3, ticket.getId());
    }

    @Test
    public void test_setAndGetParkingSpot_equalMockedParkingSpot(){

    	//GIVEN
        
    	//WHEN
    	ticket.setParkingSpot(parkingSpot);

    	//THEN
        assertEquals(parkingSpot, ticket.getParkingSpot());
    }

    @Test
    public void test_setAndGetVehiculeRegNumber_equalConstantString(){

    	//GIVEN

    	//WHEN
    	ticket.setVehicleRegNumber("ABCDEF");

    	//THEN
        assertEquals("ABCDEF", ticket.getVehicleRegNumber());
    }

    @Test
    public void test_setAndGetDiscount_equalConstantInt(){

    	//GIVEN

    	//WHEN
    	ticket.setDiscount(0.5);

    	//THEN
        assertEquals(0.5, ticket.getDiscount());
    }

    @Test
    public void test_setAndGetPrice_equalConstantInt(){

    	//GIVEN

    	//WHEN
    	ticket.setPrice(4);

    	//THEN
        assertEquals(4, ticket.getPrice());
    }

    @Test
    public void test_setAndGetInTime_equalNewDate(){

    	//GIVEN
    	Date date = new Date();

    	//WHEN
    	ticket.setInTime(date);

    	//THEN
        assertEquals(date, ticket.getInTime());
    }

    @Test
    public void test_setAndGetOutTime_equalNewDate(){
        
    	//GIVEN
    	Date date = new Date();

    	//WHEN
    	ticket.setOutTime(date);

    	//THEN
        assertEquals(date, ticket.getOutTime());
    }
}