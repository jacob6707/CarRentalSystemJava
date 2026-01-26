package dev.jacob6707.carrentalsystemjavafx.controller;

import dev.jacob6707.carrentalsystemjavafx.app.CarRentalSystemJavaFXApp;
import dev.jacob6707.carrentalsystemjavafx.model.rental.Rental;
import dev.jacob6707.carrentalsystemjavafx.model.rental.RentalDTO;
import dev.jacob6707.carrentalsystemjavafx.repository.DatabaseRentalsRepository;
import dev.jacob6707.carrentalsystemjavafx.util.DialogUtils;
import dev.jacob6707.carrentalsystemjavafx.util.RentalUtils;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the Rentals tab.
 */
public class RentalsTabController {

    @FXML
    private TableColumn<Rental, String> rentalCustomerColumn;

    @FXML
    private TableColumn<Rental, String> rentalEndDateColumn;

    @FXML
    private TableColumn<Rental, String> rentalIdColumn;

    @FXML
    private TableColumn<Rental, Number> rentalPriceColumn;

    @FXML
    private TableColumn<Rental, String> rentalStartDateColumn;

    @FXML
    private TableColumn<Rental, String> rentalVehicleColumn;

    @FXML
    private TextField rentalsSearchTextField;

    @FXML
    private TableView<Rental> rentalsTableView;

    @FXML
    private Button rentalDeleteButton;

    @FXML
    private Button rentalAddButton;

    private final DatabaseRentalsRepository databaseRentalsRepository = new DatabaseRentalsRepository();

    private static final Logger log = LoggerFactory.getLogger(RentalsTabController.class);

    private List<Rental> getAllRentals() { return databaseRentalsRepository.findAll().stream().filter(dto -> dto.getVehicleId() != null && dto.getCustomerId() != null).map(RentalDTO::constructFromDTO).toList(); }

    /**
     * Initializes table columns and populates rental data
     */
    @FXML
    void initialize() {
        rentalIdColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        rentalStartDateColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getStartDate().toString()));
        rentalEndDateColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getEndDate().toString()));
        rentalCustomerColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getCustomer().getFullName()));
        rentalVehicleColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getVehicle().toString()));
        rentalPriceColumn.setCellValueFactory(param -> new ReadOnlyDoubleWrapper(param.getValue().getPrice().doubleValue()));

        Thread.startVirtualThread(() -> {
            List<Rental> rentals = getAllRentals();
            Platform.runLater(() -> rentalsTableView.setItems(FXCollections.observableArrayList(rentals)));
        });


        rentalsTableView.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> rentalDeleteButton.setDisable(newValue == null));
    }

    /**
     * Searches rentals; displays results or warning
     */
    @FXML
    void onRentalSearchAction() {
        List<Rental> rentals = getAllRentals();
        if (rentalsSearchTextField.getText().isBlank()) {
            rentalsTableView.setItems(FXCollections.observableArrayList(rentals));
            return;
        }
        List<Rental> filteredRentals = RentalUtils.searchRentals(rentals, rentalsSearchTextField.getText());
        if (filteredRentals.isEmpty()) {
            DialogUtils.showWarningDialog("Warning", "No rentals found.", "No rentals were found with the given search term.");
            return;
        }
        rentalsTableView.setItems(FXCollections.observableArrayList(filteredRentals));
    }

    /**
     * Opens the add rental view.
     */
    @FXML
    void onRentalAddAction() {
        try {
            Stage parent = (Stage) rentalAddButton.getScene().getWindow();
            Stage addRentalStage = new Stage();
            Scene addRentalScene = new Scene(new FXMLLoader(CarRentalSystemJavaFXApp.class.getResource("add-rental-view.fxml")).load());
            addRentalStage.setTitle("Add Vehicle");
            addRentalStage.initOwner(parent);
            addRentalStage.setScene(addRentalScene);
            addRentalStage.showAndWait();
            rentalsTableView.setItems(FXCollections.observableArrayList(getAllRentals()));
        } catch (IOException e) {
            DialogUtils.showErrorDialog("Error", "Failed to load add rental view.", "An unexpected error occurred. Please try again later.");
            log.error("Failed to load add rental view.", e);
        }
    }

    /**
     * Deletes the selected rental.
     * @param event The event that triggered this method
     */
    @FXML
    void onRentalDeleteAction(ActionEvent event) {
        if (rentalsTableView.getSelectionModel().getSelectedItem() == null) return;
        Rental rentalToDelete = rentalsTableView.getSelectionModel().getSelectedItem();
        DialogUtils.showConfirmationDialog("Delete Rental", "Are you sure you want to delete this rental?", "This action cannot be undone.")
                .filter(response -> response == ButtonType.OK)
                .ifPresent(_ -> {
                    databaseRentalsRepository.deleteById(rentalToDelete.getId());
                    rentalsTableView.setItems(FXCollections.observableArrayList(getAllRentals()));
                });
        log.info("Rental deleted successfully: {}", rentalToDelete);
    }

    /**
     * Refreshes the table view with the latest data from the database.
     */
    public void refresh() {
        Thread.startVirtualThread(() -> {
            List<Rental> rentals = getAllRentals();
            Platform.runLater(() -> rentalsTableView.setItems(FXCollections.observableArrayList(rentals)));
        });
    }
}
