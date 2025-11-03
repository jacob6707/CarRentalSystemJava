package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.exception.InvalidNumericValueException;
import dev.jacob6707.carrentalsystem.exception.InvalidDateFormatException;
import dev.jacob6707.carrentalsystem.exception.NegativeValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Service for getting user input.
 */
public class InputService {
    private static final Logger logger = LoggerFactory.getLogger(InputService.class);

    /**
     * Reads and validates a numeric choice from the user.
     * Handles invalid input by catching exceptions and prompting the user to retry.
     *
     * @param sc Scanner object for reading user input
     * @param min minimum valid value (inclusive)
     * @param max maximum valid value (inclusive)
     * @return validated integer choice from the user
     * @throws InvalidNumericValueException if the input cannot be parsed as an integer
     */
    public static Integer readValidatedInteger(Scanner sc, Integer min, Integer max) throws InvalidNumericValueException {
        logger.debug("Reading validated integer input between {} and {}", min, max);
        String input = sc.nextLine();

        try {
            Integer value = Integer.parseInt(input);
            logger.trace("Parsed integer value: {}", value);

            // Unchecked exception for negative values where positive is required
            if (value < 0 && min >= 0) {
                logger.error("Negative value {} provided where positive required", value);
                throw new NegativeValueException("Value cannot be negative. Please enter a positive number.");
            }

            if (value < min || value > max) {
                logger.warn("Value {} out of range [{}, {}]", value, min, max);
                throw new InvalidNumericValueException("Value must be between " + min + " and " + max);
            }

            logger.info("Valid integer {} received", value);
            return value;
        } catch (NumberFormatException e) {
            logger.error("Invalid numeric input: {}", input, e);
            throw new InvalidNumericValueException("Invalid input. Please enter a valid number.", e);
        }
    }

    /**
     * Reads and validates a positive integer from user input.
     * Handles both checked exceptions (invalid input) and unchecked exceptions (negative values).
     *
     * @param sc Scanner object for reading user input
     * @param prompt Message to display to the user
     * @return validated positive integer
     */
    public static Integer readPositiveInteger(Scanner sc, String prompt) {
        logger.debug("Reading positive integer with prompt: {}", prompt);
        Integer value = null;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine();
                logger.trace("Received input: {}", input);

                value = Integer.parseInt(input);

                // Unchecked exception for negative values
                if (value < 0) {
                    logger.error("Negative value provided: {}", value);
                    throw new NegativeValueException("Value cannot be negative. Please enter a positive number.");
                }

                valid = true;
                logger.info("Valid positive integer received: {}", value);
            } catch (NumberFormatException e) {
                logger.warn("Invalid numeric input", e);
                System.out.println("Error: Invalid input. Please enter a valid number.");
            } catch (NegativeValueException e) {
                logger.warn("Negative value error: {}", e.getMessage());
                System.out.println("Error: " + e.getMessage());
            }
        }

        return value;
    }

    /**
     * Reads and validates a date from user input.
     * Handles InvalidDateFormatException for improper date formats.
     *
     * @param sc Scanner object for reading user input
     * @param prompt Message to display to the user
     * @return validated LocalDate object
     * @throws InvalidDateFormatException if date format is invalid
     */
    public static LocalDate readValidatedDate(Scanner sc, String prompt, String pattern) throws InvalidDateFormatException {
        logger.debug("Reading validated date with prompt: {}", prompt);
        System.out.print(prompt);
        String input = sc.nextLine();
        logger.trace("Received date input: {}", input);

        try {
            LocalDate date = LocalDate.parse(input, DateTimeFormatter.ofPattern(pattern));
            logger.info("Valid date parsed: {}", date);
            return date;
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format: {}", input, e);
            throw new InvalidDateFormatException("Invalid date format. Please use " + pattern + " format.", e);
        }
    }

    /**
     * Reads and validates a BigDecimal value from user input.
     * Handles InvalidNumericInputException for non-numeric input and NegativeValueException for negative amounts.
     *
     * @param sc Scanner object for reading user input
     * @param prompt Message to display to the user
     * @return validated BigDecimal value
     * @throws InvalidNumericValueException if input cannot be parsed as a number
     */
    public static BigDecimal readPositiveBigDecimal(Scanner sc, String prompt) throws InvalidNumericValueException {
        logger.debug("Reading positive BigDecimal with prompt: {}", prompt);
        System.out.print(prompt);
        String input = sc.nextLine();
        logger.trace("Received BigDecimal input: {}", input);

        try {
            BigDecimal value = new BigDecimal(input);

            // Unchecked exception for negative salary
            if (value.compareTo(BigDecimal.ZERO) < 0) {
                logger.error("Negative BigDecimal value provided: {}", value);
                throw new NegativeValueException("Value cannot be negative. Please enter a positive amount.");
            }

            logger.info("Valid positive BigDecimal received: {}", value);
            return value;
        } catch (NumberFormatException e) {
            logger.error("Invalid BigDecimal input: {}", input, e);
            throw new InvalidNumericValueException("Invalid input. Please enter a valid numeric amount.", e);
        }
    }


}
