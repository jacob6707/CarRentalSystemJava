package dev.jacob6707.carrentalsystemjavafx.controller;

import dev.jacob6707.carrentalsystemjavafx.app.CarRentalSystemJavaFXApp;
import dev.jacob6707.carrentalsystemjavafx.model.vehicle.Vehicle;
import dev.jacob6707.carrentalsystemjavafx.repository.VehiclesRepository;
import dev.jacob6707.carrentalsystemjavafx.util.DialogUtils;
import dev.jacob6707.carrentalsystemjavafx.util.VehicleUtils;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class VehiclesTabController {
    @FXML
    private Button vehicleAddButton;

    @FXML
    private Button vehicleDeleteButton;

    @FXML
    private TextField vehiclesSearchTextField;

    @FXML
    private TableColumn<Vehicle, String> vehicleIdColumn;

    @FXML
    private TableColumn<Vehicle, String> vehicleBrandColumn;

    @FXML
    private TableColumn<Vehicle, String> vehicleModelColumn;

    @FXML
    private TableColumn<Vehicle, String> vehicleLicensePlateColumn;

    @FXML
    private TableColumn<Vehicle, Number> vehicleMileageColumn;

    @FXML
    private TableColumn<Vehicle, Number> vehicleYearColumn;

    @FXML
    private TableView<Vehicle> vehiclesTableView;

    private final VehiclesRepository vehiclesRepository = VehiclesRepository.getInstance();
    private static final Logger log = LoggerFactory.getLogger(VehiclesTabController.class);

    @FXML
    void initialize() {
        vehicleIdColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        vehicleBrandColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getBrand()));
        vehicleModelColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getModel()));
        vehicleLicensePlateColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getLicensePlate()));
        vehicleMileageColumn.setCellValueFactory(param -> new ReadOnlyIntegerWrapper(param.getValue().getMileage()));
        vehicleYearColumn.setCellValueFactory(param -> new ReadOnlyIntegerWrapper(param.getValue().getYear()));

        vehiclesTableView.setItems(FXCollections.observableArrayList(vehiclesRepository.findAll()));

        vehiclesTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> vehicleDeleteButton.setDisable(newValue == null));
    }

    @FXML
    void onVehicleSearchAction() {
        if (vehiclesSearchTextField.getText().isBlank()) {
            vehiclesTableView.setItems(FXCollections.observableArrayList(vehiclesRepository.findAll()));
            return;
        }
        List<Vehicle> filteredVehicles = VehicleUtils.searchVehicles(vehiclesRepository.findAll(), vehiclesSearchTextField.getText());
        if (filteredVehicles.isEmpty()) {
            DialogUtils.showWarningDialog("Warning", "No vehicles found.", "No vehicles were found with the given search term.");
            return;
        }
        vehiclesTableView.setItems(FXCollections.observableArrayList(filteredVehicles));
    }

    @FXML
    void onVehicleDeleteAction() {
        DialogUtils.showConfirmationDialog("Delete Vehicle", "Are you sure you want to delete this vehicle?", "This action cannot be undone.")
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    vehiclesRepository.deleteById(vehiclesTableView.getSelectionModel().getSelectedItem().getId());
                    vehiclesTableView.setItems(FXCollections.observableArrayList(vehiclesRepository.findAll()));
                });
    }

    @FXML
    void onVehicleAddAction() {
        try {
            Stage parent = (Stage) vehicleAddButton.getScene().getWindow();
            Stage addVehicleStage = new Stage();
            Scene addVehicleScene = new Scene(new FXMLLoader(CarRentalSystemJavaFXApp.class.getResource("add-vehicle-view.fxml")).load());
            addVehicleStage.setTitle("Add Vehicle");
            addVehicleStage.initOwner(parent);
            addVehicleStage.setScene(addVehicleScene);
            addVehicleStage.showAndWait();
            vehiclesTableView.setItems(FXCollections.observableArrayList(vehiclesRepository.findAll()));
        } catch (IOException e) {
            DialogUtils.showErrorDialog("Error", "Failed to load add vehicle view.", "An unexpected error occurred. Please try again later.");
            log.error("Failed to load add vehicle view.", e);
        }
    }
}
