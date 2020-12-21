package com.parkit.parkingsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * reads both int and String input provided by a user.
 * it allows interaction between the user and the ParkingSystem
 */
public class InputReaderUtil {

    /**
     * allows users to provide their choices to the ParkingSystem.
     */
    private Scanner scan = new Scanner(System.in);

    /**
     * error message to be displayed.
     */
    private static final String ERROR_MESSAGE
            = "Error while reading user input from Shell";
    /**
     * Setter of the inputReaderUtil scanner.
     * @param scanner
     */
    public void setScan(final Scanner scanner) {
        this.scan = scanner;
    }
    /**
     * InputReaderUtil logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger("InputReaderUtil");

    /**
     * asks the user to enter an integer.
     * @return the entered integer if the provide value is correct
     * or -1 if the value is incorrect
     */
    public int readSelection() {
        try {
            int input = Integer.parseInt(scan.nextLine());
            if (input > 0 && input < 4) {
                return input;
            } else {
                LOGGER.error(ERROR_MESSAGE);
                return -1;
            }
        } catch (Exception e) {
            LOGGER.error(ERROR_MESSAGE, e);
            LOGGER.error("Error reading input. "
                    + "Please enter valid number for proceeding further");
            return -1;
        }
    }

    /**
     * allows the user to enter his/her vehicle registration number.
     * @return String: the vehicle reg number if the provided value is correct
     * @throws Exception if the provided value is incorrect
     */
    public String readVehicleRegistrationNumber()  {
        try {
            String vehicleRegNumber = scan.nextLine();
            if (vehicleRegNumber == null
                    || vehicleRegNumber.trim().length() == 0) {
                throw new IllegalArgumentException("Invalid input provided");
            }
            return vehicleRegNumber;
        } catch (Exception e) {
            LOGGER.error(ERROR_MESSAGE, e);
            LOGGER.error("Error reading input. Please enter "
                    + "a valid string for vehicle registration number");
            throw e;
        }
    }
}