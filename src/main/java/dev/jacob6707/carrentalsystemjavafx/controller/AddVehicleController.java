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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
            DialogUtils.showWarningDialog("Warning", "Invalid input.", "Please make sure all fields are filled correctly.");
            return;
        }

        switch (type) {
            case "SUV" ->
                    DialogUtils.showConfirmationDialog("Add SUV", "Are you sure you want to add this SUV?", "This action cannot be undone.")
                            .filter(response -> response == ButtonType.OK)
                            .ifPresent(response -> VehiclesRepository.getInstance().save(new SUV.SUVBuilder()
                                    .id(UUID.randomUUID())
                                    .brand(brand)
                                    .model(model)
                                    .licensePlate(licensePlate)
                                    .mileage(mileage)
                                    .year(year)
                                    .dailyPrice(dailyPrice)
                                    .build()));
            case "Car" -> DialogUtils.showConfirmationDialog("Add Car", "Are you sure you want to add this Car?", "This action cannot be undone.")
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> VehiclesRepository.getInstance().save(new Car.CarBuilder()
                            .id(UUID.randomUUID())
                            .brand(brand)
                            .model(model)
                            .licensePlate(licensePlate)
                            .mileage(mileage)
                            .year(year)
                            .dailyPrice(dailyPrice)
                            .build()));
            default -> DialogUtils.showWarningDialog("Warning", "Invalid input.", "Please make sure all fields are filled correctly.");
        }

        Stage stage = (Stage) addVehicleButton.getScene().getWindow();
        stage.close();
    }

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
