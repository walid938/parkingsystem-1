package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ParkingSpotTest {

	private ParkingSpot parkingSpot;

    @BeforeEach
    private void setUpPerTest() {
    	parkingSpot = new ParkingSpot(1,ParkingType.CAR,false);
    }

    @Test
    public void test_setAndGetID_equalConstantInt(){

    	//GIVEN
        
    	//WHEN
    	parkingSpot.setId(3);
    	
    	//THEN
        assertEquals(3, parkingSpot.getId());
    }

    @Test
    public void test_setAndGetParkingType_equalParkingTypeCar(){

    	//GIVEN

    	//WHEN
    	parkingSpot.setParkingType(ParkingType.CAR);

    	//THEN
        assertEquals(ParkingType.CAR, parkingSpot.getParkingType());
    }

    @Test
    public void test_setAndGetParkingType_equalParkingTypeBike(){

    	//GIVEN

    	//WHEN
    	parkingSpot.setParkingType(ParkingType.BIKE);

    	//THEN
        assertEquals(ParkingType.BIKE, parkingSpot.getParkingType());
    }

    @Test
    public void test_setAndGetAvailability_equalTrue(){

    	//GIVEN

    	//WHEN
    	parkingSpot.setAvailable(true);

    	//THEN
        assertEquals(true, parkingSpot.isAvailable());
    }

    @Test
    public void test_equals_equalTrue_whenHimself(){

    	//GIVEN

    	//WHEN

    	//THEN
        assertEquals(true, parkingSpot.equals(parkingSpot));
    }

    @Test
    public void test_equals_equalTrue_whenAnotherInstance(){

    	//GIVEN

    	//WHEN

    	//THEN
        assertEquals(true, parkingSpot.equals(new ParkingSpot(1,ParkingType.CAR,false)));
    }

    @Test
    public void test_equals_equalFalse_whenNull(){

    	//GIVEN

    	//WHEN

    	//THEN
        assertEquals(false, parkingSpot.equals(null));
    }

    @Test
    public void test_equals_equalFalse_whenAnotherClass(){

    	//GIVEN

    	//WHEN

    	//THEN
        assertEquals(false, parkingSpot.equals(new String()));
    }

    @Test
    public void test_hashCode_equalConstantInt(){

    	//GIVEN

    	//WHEN
    	parkingSpot.setId(4);

    	//THEN
        assertEquals(4, parkingSpot.hashCode());
    }
}
