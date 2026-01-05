package dev.jacob6707.carrentalsystemjavafx.controller;

import dev.jacob6707.carrentalsystemjavafx.model.person.Customer;
import dev.jacob6707.carrentalsystemjavafx.model.rental.Rentable;
import dev.jacob6707.carrentalsystemjavafx.model.rental.Rental;
import dev.jacob6707.carrentalsystemjavafx.model.vehicle.Vehicle;
import dev.jacob6707.carrentalsystemjavafx.repository.CustomersRepository;
import dev.jacob6707.carrentalsystemjavafx.repository.RentalsRepository;
import dev.jacob6707.carrentalsystemjavafx.repository.VehiclesRepository;
import dev.jacob6707.carrentalsystemjavafx.util.DialogUtils;
import dev.jacob6707.carrentalsystemjavafx.util.VehicleUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class AddRentalController {

    @FXML
    private Button addRentalButton;

    @FXML
    private Label calculatedCostLabel;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private Spinner<Integer> rentalDaysSpinner;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<Vehicle> vehicleComboBox;

    private final RentalsRepository rentalsRepository = RentalsRepository.getInstance();
    private final VehiclesRepository vehiclesRepository = VehiclesRepository.getInstance();
    private final CustomersRepository customersRepository = CustomersRepository.getInstance();

    private static final Logger log = LoggerFactory.getLogger(AddRentalController.class);

    /**
     * Initializes the view.
     */
    @FXML
    public void initialize() {
        customerComboBox.setItems(FXCollections.observableArrayList(customersRepository.findAll()));
        vehicleComboBox.setItems(FXCollections.observableArrayList(VehicleUtils.getAvailableVehicles(vehiclesRepository.findAll(), rentalsRepository.findAll())));
        startDatePicker.setValue(LocalDate.now());
        rentalDaysSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999, 0));
        rentalDaysSpinner.valueProperty().addListener((_, _, _) -> updateCostLabel());
    }

    /**
     * Helper method to update the cost label to match what it would be if the rental was completed.
     */
    private void updateCostLabel() {
        if (!(vehicleComboBox.getValue() instanceof Rentable rentable) || rentalDaysSpinner.getValue() < 0) return;
        BigDecimal cost = rentable.getDailyPrice().multiply(BigDecimal.valueOf(rentalDaysSpinner.getValue()));
        calculatedCostLabel.setText("Calculated rental cost: €" + cost.setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Handles rental creation; persists if input is valid.
     */
    @FXML
    void addRentalAction() {
        Customer customer = customerComboBox.getValue();
        Vehicle vehicle = vehicleComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        int rentalDays = rentalDaysSpinner.getValue();

        if (customer == null || vehicle == null || startDate == null || rentalDays <= 0) {
            log.warn("Invalid rental details: customer: {}, vehicle: {}, startDate: {}, rentalDays: {}", customer, vehicle, startDate, rentalDays);
            DialogUtils.showWarningDialog("Warning", "Invalid rental details.", "Please make sure all fields are filled correctly.");
            return;
        }

        Rental newRental = new Rental(customer, vehicle, startDate.atTime(12, 0), startDate.atTime(12, 0).plusDays(rentalDays));

        rentalsRepository.save(newRental);

        log.info("Rental created: {}", newRental);

        Stage stage = (Stage) addRentalButton.getScene().getWindow();
        stage.close();
    }

}
