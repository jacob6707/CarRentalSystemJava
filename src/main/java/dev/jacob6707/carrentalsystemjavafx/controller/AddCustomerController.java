package dev.jacob6707.carrentalsystemjavafx.controller;

import dev.jacob6707.carrentalsystemjavafx.model.Location;
import dev.jacob6707.carrentalsystemjavafx.model.person.Customer;
import dev.jacob6707.carrentalsystemjavafx.repository.CustomersRepository;
import dev.jacob6707.carrentalsystemjavafx.util.CustomerUtils;
import dev.jacob6707.carrentalsystemjavafx.util.DialogUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

public class AddCustomerController {

    @FXML
    private Button addCustomerButton;

    @FXML
    private TextField addCustomerDateOfBirthTextField;

    @FXML
    private TextField addCustomerEmailTextField;

    @FXML
    private TextField addCustomerFirstNameTextField;

    @FXML
    private TextField addCustomerIDNumberTextField;

    @FXML
    private TextField addCustomerLastNameTextField;

    @FXML
    private TextField addCustomerLocationTextField;

    @FXML
    private TextField addCustomerPhoneNumberTextField;

    @FXML
    void addCustomerAction(ActionEvent event) {
        String firstName = addCustomerFirstNameTextField.getText();
        String lastName = addCustomerLastNameTextField.getText();
        String email = addCustomerEmailTextField.getText();
        String phoneNumber = addCustomerPhoneNumberTextField.getText();
        String idNumber = addCustomerIDNumberTextField.getText();
        String location = addCustomerLocationTextField.getText();
        String dateOfBirth = addCustomerDateOfBirthTextField.getText();

        if (!CustomerUtils.validateInput(firstName, lastName, email, phoneNumber, idNumber, location, dateOfBirth)) {
            DialogUtils.showWarningDialog("Warning", "Invalid input.", "Please make sure all fields are filled correctly.");
            return;
        }

        Location location1 = getLocation(location);

        CustomersRepository.getInstance().save(new Customer.CustomerBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .idNumber(idNumber)
                .location(location1)
                .dateOfBirth(LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .build());

        Stage stage = (Stage) addCustomerButton.getScene().getWindow();
        stage.close();
    }

    private static Location getLocation(String location) {
        StringTokenizer locationTokenizer = new StringTokenizer(location, ",");
        String address = locationTokenizer.nextToken().trim();
        String cityAndPostalCode = locationTokenizer.nextToken().trim();
        String postalCode = cityAndPostalCode.substring(0, cityAndPostalCode.indexOf(" "));
        String city = cityAndPostalCode.substring(cityAndPostalCode.indexOf(" ") + 1);
        String state = locationTokenizer.nextToken().trim();
        String country = locationTokenizer.nextToken().trim();

        return new Location(address, city, state, postalCode, country);
    }
}
