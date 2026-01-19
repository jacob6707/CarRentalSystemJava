package dev.jacob6707.carrentalsystemjavafx.controller;

import dev.jacob6707.carrentalsystemjavafx.model.Location;
import dev.jacob6707.carrentalsystemjavafx.model.person.Customer;
import dev.jacob6707.carrentalsystemjavafx.repository.DatabaseCustomersRepository;
import dev.jacob6707.carrentalsystemjavafx.util.CustomerUtils;
import dev.jacob6707.carrentalsystemjavafx.util.DialogUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Controller for the Add Customer screen.
 */
public class AddCustomerController {

    @FXML
    private Button addCustomerButton;

    @FXML
    private DatePicker addCustomerDateOfBirthDatePicker;

    @FXML
    private TextField addCustomerEmailTextField;

    @FXML
    private TextField addCustomerFirstNameTextField;

    @FXML
    private TextField addCustomerIDNumberTextField;

    @FXML
    private TextField addCustomerLastNameTextField;

    @FXML
    private TextField addCustomerAddressTextField;

    @FXML
    private TextField addCustomerCityTextField;

    @FXML
    private TextField addCustomerStateTextField;

    @FXML
    private TextField addCustomerPostalCodeTextField;

    @FXML
    private TextField addCustomerCountryTextField;

    @FXML
    private TextField addCustomerPhoneNumberTextField;

    private static final Logger log = LoggerFactory.getLogger(AddCustomerController.class);

    @FXML
    void initialize() {
        addCustomerDateOfBirthDatePicker.setValue(LocalDate.now());
    }

    /**
     * Handles customer creation; persists if input is valid.
     * @param event The event that triggered this method
     */
    @FXML
    void addCustomerAction(ActionEvent event) {
        String firstName = addCustomerFirstNameTextField.getText();
        String lastName = addCustomerLastNameTextField.getText();
        String email = addCustomerEmailTextField.getText();
        String phoneNumber = addCustomerPhoneNumberTextField.getText();
        String idNumber = addCustomerIDNumberTextField.getText();
        String address = addCustomerAddressTextField.getText();
        String city = addCustomerCityTextField.getText();
        String state = addCustomerStateTextField.getText();
        String postalCode = addCustomerPostalCodeTextField.getText();
        String country = addCustomerCountryTextField.getText();
        LocalDate dateOfBirth = addCustomerDateOfBirthDatePicker.getValue();

        Location location = new Location(address,city,state,postalCode,country);
        if (!CustomerUtils.validateInput(firstName, lastName, email, phoneNumber, idNumber, location) || dateOfBirth == null) {
            log.warn("Invalid input for customer creation: {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}", firstName, lastName, email, phoneNumber, idNumber, address, city, state, postalCode, country, dateOfBirth);
            DialogUtils.showWarningDialog("Warning", "Invalid input.", "Please make sure all fields are filled correctly.");
            return;
        }

        Customer newCustomer = new Customer.CustomerBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .idNumber(idNumber)
                .location(location)
                .dateOfBirth(dateOfBirth)
                .discountRate(BigDecimal.ZERO)
                .build();

        new DatabaseCustomersRepository().save(newCustomer);

        log.info("Customer created: {}", newCustomer);

        Stage stage = (Stage) addCustomerButton.getScene().getWindow();
        stage.close();
    }
}
