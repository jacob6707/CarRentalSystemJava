package dev.jacob6707.carrentalsystemjavafx.controller;

import dev.jacob6707.carrentalsystemjavafx.model.rental.Rental;
import dev.jacob6707.carrentalsystemjavafx.repository.RentalsRepository;
import dev.jacob6707.carrentalsystemjavafx.util.DialogUtils;
import dev.jacob6707.carrentalsystemjavafx.util.RentalUtils;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    private final RentalsRepository rentalsRepository = RentalsRepository.getInstance();

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

        rentalsTableView.setItems(FXCollections.observableArrayList(rentalsRepository.findAll()));
    }

    /**
     * Searches rentals; displays results or warning
     */
    @FXML
    void onRentalSearchAction() {
        if (rentalsSearchTextField.getText().isBlank()) {
            rentalsTableView.setItems(FXCollections.observableArrayList(rentalsRepository.findAll()));
            return;
        }
        List<Rental> filteredRentals = RentalUtils.searchRentals(rentalsRepository.findAll(), rentalsSearchTextField.getText());
        if (filteredRentals.isEmpty()) {
            DialogUtils.showWarningDialog("Warning", "No rentals found.", "No rentals were found with the given search term.");
            return;
        }
        rentalsTableView.setItems(FXCollections.observableArrayList(filteredRentals));
    }
}
