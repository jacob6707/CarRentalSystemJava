package dev.jacob6707.carrentalsystemjavafx.controller;

import dev.jacob6707.carrentalsystemjavafx.app.CarRentalSystemJavaFXApp;
import dev.jacob6707.carrentalsystemjavafx.model.person.Customer;
import dev.jacob6707.carrentalsystemjavafx.repository.CustomersRepository;
import dev.jacob6707.carrentalsystemjavafx.util.CustomerUtils;
import dev.jacob6707.carrentalsystemjavafx.util.DialogUtils;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller for the Customers tab.
 */
public class CustomersTabController {

    @FXML
    private Button customerAddButton;

    @FXML
    private TableColumn<Customer, String> customerDateOfBirthColumn;

    @FXML
    private Button customerDeleteButton;

    @FXML
    private TableColumn<Customer, Number> customerDiscountRateColumn;

    @FXML
    private TableColumn<Customer, String> customerEmailColumn;

    @FXML
    private TableColumn<Customer, String> customerFirstNameColumn;

    @FXML
    private TableColumn<Customer, String> customerIDNumberColumn;

    @FXML
    private TableColumn<Customer, String> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerLastNameColumn;

    @FXML
    private TableColumn<Customer, String> customerLocationColumn;

    @FXML
    private TableColumn<Customer, String> customerPhoneNumberColumn;

    @FXML
    private TextField customersSearchTextField;

    @FXML
    private TableView<Customer> customersTableView;

    private final CustomersRepository customersRepository = CustomersRepository.getInstance();
    private static final Logger log = LoggerFactory.getLogger(CustomersTabController.class);

    /**
     * Initializes table columns and populates customer data
     */
    @FXML
    void initialize() {
        customerIdColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        customerFirstNameColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getFirstName()));
        customerLastNameColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getLastName()));
        customerEmailColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getEmail()));
        customerPhoneNumberColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getPhoneNumber()));
        customerLocationColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getLocation().toString()));
        customerIDNumberColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getIdNumber()));
        customerDateOfBirthColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        customerDiscountRateColumn.setCellValueFactory(param -> new ReadOnlyDoubleWrapper(param.getValue().getDiscountRate().doubleValue()));

        customersTableView.setItems(FXCollections.observableArrayList(customersRepository.findAll()));

        customersTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> customerDeleteButton.setDisable(newValue == null));

    }

    /**
     * Opens the add customer view.
     * @param event The event that triggered this method
     */
    @FXML
    void onCustomerAddAction(ActionEvent event) {
        try {
            Stage parent = (Stage) customerAddButton.getScene().getWindow();
            Stage addCustomerStage = new Stage();
            Scene addCustomerScene = new Scene(new FXMLLoader(CarRentalSystemJavaFXApp.class.getResource("add-customer-view.fxml")).load());
            addCustomerStage.setTitle("Add Customer");
            addCustomerStage.initOwner(parent);
            addCustomerStage.setScene(addCustomerScene);
            addCustomerStage.showAndWait();
            customersTableView.setItems(FXCollections.observableArrayList(customersRepository.findAll()));
        } catch (IOException e) {
            DialogUtils.showErrorDialog("Error", "Failed to load add customer view.", "An unexpected error occurred. Please try again later.");
            log.error("Failed to load add customer view.", e);
        }
    }

    /**
     * Deletes the selected customer.
     * @param event The event that triggered this method
     */
    @FXML
    void onCustomerDeleteAction(ActionEvent event) {
        if (customersTableView.getSelectionModel().getSelectedItem() == null) return;
        Customer customerToDelete = customersTableView.getSelectionModel().getSelectedItem();
        DialogUtils.showConfirmationDialog("Delete Customer", "Are you sure you want to delete this customer?", "This action cannot be undone.")
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    customersRepository.deleteById(customerToDelete.getId());
                    customersTableView.setItems(FXCollections.observableArrayList(customersRepository.findAll()));
                });
        log.info("Customer deleted successfully: {}", customerToDelete);
    }

    /**
     * Searches for customers based on the search term.
     * @param event The event that triggered this method
     */
    @FXML
    void onCustomerSearchAction(ActionEvent event) {
        if (customersSearchTextField.getText().isBlank()) {
            customersTableView.setItems(FXCollections.observableArrayList(customersRepository.findAll()));
            return;
        }
        List<Customer> filteredCustomers = CustomerUtils.searchCustomers(customersRepository.findAll(), customersSearchTextField.getText());
        if (filteredCustomers.isEmpty()) {
            DialogUtils.showWarningDialog("Warning", "No customers found.", "No customers were found with the given search term.");
            return;
        }
        customersTableView.setItems(FXCollections.observableArrayList(filteredCustomers));
    }


}

