package dev.jacob6707.carrentalsystemjavafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main view controller
 */
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    @FXML
    private TabPane mainTabPane;
    @FXML
    private MenuBarController menuBarController;
    @FXML
    private CustomersTabController customersTabController;
    @FXML
    private VehiclesTabController vehiclesTabController;
    @FXML
    private RentalsTabController rentalsTabController;

    /**
     * Initializes the view.
     */
    public void initialize() {
        menuBarController.setMainController(this);
    }

    /**
     * Gets the ID of the currently selected tab.
     * @return Tab ID
     */
    public String getActiveTab() {
        return mainTabPane.getSelectionModel().getSelectedItem().getId();
    }

    /**
     * Refreshes data based on currently selected tab
     */
    public void refreshActiveTab() {
        switch(getActiveTab()) {
            case "vehicles" -> vehiclesTabController.refresh();
            case "rentals" -> rentalsTabController.refresh();
            case "customers" -> customersTabController.refresh();
            default -> log.trace("Invalid tab selected: {}", getActiveTab());
        }
    }
}
