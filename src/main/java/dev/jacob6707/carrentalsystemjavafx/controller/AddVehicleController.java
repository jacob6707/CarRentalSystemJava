package dev.jacob6707.carrentalsystemjavafx.controller;

import dev.jacob6707.carrentalsystemjavafx.model.vehicle.Car;
import dev.jacob6707.carrentalsystemjavafx.model.vehicle.SUV;
import dev.jacob6707.carrentalsystemjavafx.repository.VehiclesRepository;
import dev.jacob6707.carrentalsystemjavafx.util.DialogUtils;
import dev.jacob6707.carrentalsystemjavafx.util.VehicleUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Controller for the Add Vehicle screen.
 */
public class AddVehicleController {

    @FXML
    private TextField addVehicleBrandTextField;

    @FXML
    private Button addVehicleButton;

    @FXML
    private TextField addVehicleLicensePlateTextField;

    @FXML
    private Spinner<Integer> addVehicleMileageSpinner;

    @FXML
    private TextField addVehicleModelTextField;

    @FXML
    private Spinner<Double> addVehiclePriceSpinner;

    @FXML
    private ComboBox<String> addVehicleTypeComboBox;

    @FXML
    private Spinner<Integer> addVehicleYearSpinner;

    private static final Logger log = LoggerFactory.getLogger(AddVehicleController.class);

    /**
     * Handles vehicle creation; persists if input is valid.
     * @param event The event that triggered this method
     */
    @FXML
    void addVehicleAction(ActionEvent event) {
        String type = addVehicleTypeComboBox.getValue();
        String brand = addVehicleBrandTextField.getText();
        String model = addVehicleModelTextField.getText();
        String licensePlate = addVehicleLicensePlateTextField.getText();
        Integer mileage = addVehicleMileageSpinner.getValue();
        Integer year = addVehicleYearSpinner.getValue();
        BigDecimal dailyPrice = BigDecimal.valueOf(addVehiclePriceSpinner.getValue());

        if (!VehicleUtils.validateInput(type, brand, model, licensePlate, year, mileage, dailyPrice)) {
            log.warn("Invalid vehicle input: {}, {}, {}, {}, {}, {}, {}", type, brand, model, licensePlate, year, mileage, dailyPrice);
            DialogUtils.showWarningDialog("Warning", "Invalid input.", "Please make sure all fields are filled correctly.");
            return;
        }

        switch (type) {
            case "SUV" -> VehiclesRepository.getInstance().save(new SUV.SUVBuilder()
                                    .id(UUID.randomUUID())
                                    .brand(brand)
                                    .model(model)
                                    .licensePlate(licensePlate)
                                    .mileage(mileage)
                                    .year(year)
                                    .dailyPrice(dailyPrice)
                                    .build());
            case "Car" -> VehiclesRepository.getInstance().save(new Car.CarBuilder()
                            .id(UUID.randomUUID())
                            .brand(brand)
                            .model(model)
                            .licensePlate(licensePlate)
                            .mileage(mileage)
                            .year(year)
                            .dailyPrice(dailyPrice)
                            .build());
            default -> DialogUtils.showWarningDialog("Warning", "Invalid input.", "Please make sure all fields are filled correctly.");
        }

        log.info("Vehicle added successfully: {}", VehiclesRepository.getInstance().findAll().getLast());

        Stage stage = (Stage) addVehicleButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the view.
     */
    @FXML
    public void initialize() {
        String[] carTypes = {"Car", "SUV"};
        addVehicleTypeComboBox.setItems(FXCollections.observableArrayList(carTypes));
        addVehicleTypeComboBox.getSelectionModel().selectFirst();
        addVehicleMileageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        addVehicleYearSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2030, LocalDate.now().getYear()));
        addVehiclePriceSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE));
    }
}
