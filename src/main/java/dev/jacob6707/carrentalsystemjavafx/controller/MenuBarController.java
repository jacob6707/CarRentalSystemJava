package dev.jacob6707.carrentalsystemjavafx.controller;

import dev.jacob6707.carrentalsystemjavafx.exception.DatabaseException;
import dev.jacob6707.carrentalsystemjavafx.util.DialogUtils;
import dev.jacob6707.carrentalsystemjavafx.util.database.DatabaseUtils;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Menu bar controller
 */
public class MenuBarController {
    private static final Logger log = LoggerFactory.getLogger(MenuBarController.class);

    private MainController mainController;

    /**
     * Sets the main controller.
     * @param mainController main controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Closes the application.
     */
    public void closeApp() {
        System.exit(0);
    }

    /**
     * Backs up table after confirmation; handles errors
     */
    public void backupTable() {
        if(mainController == null) return;
        String tableName = mainController.getActiveTab();
        DialogUtils.showConfirmationDialog("Confirm backup", "Back up table " + tableName + "?", "This action cannot be undone.")
                .ifPresent(response -> {
                    if(response != ButtonType.OK) return;
                    Thread.startVirtualThread(() -> {
                        try {
                            int count = DatabaseUtils.backupTable(tableName);
                            if (count < 0) Platform.runLater(() -> DialogUtils.showWarningDialog("Warning", "Failed to back up table " + tableName, "Please make sure the table exists."));
                            mainController.refreshActiveTab();
                            Platform.runLater(() -> DialogUtils.showInfoDialog("Success", "Table " + tableName + " successfully backed up", ""));
                        } catch (DatabaseException e) {
                            Platform.runLater(() -> DialogUtils.showErrorDialog("Error", "Failed to back up table " + tableName, e.getMessage()));
                            log.error("Failed to back up table", e);
                        }
                    });
                });
    }

    /**
     * Restores table after confirmation; handles errors
     */
    public void restoreTable() {
        if(mainController == null) return;
        String tableName = mainController.getActiveTab();
        DialogUtils.showConfirmationDialog("Confirm restore", "Restore table " + tableName + "?", "This action cannot be undone.")
                .ifPresent(response -> {
                    if(response != ButtonType.OK) return;
                    Thread.startVirtualThread(() -> {
                        try {
                            int count = DatabaseUtils.restoreTable(tableName);
                            if (count < 0) Platform.runLater(() -> DialogUtils.showWarningDialog("Warning", "Failed to restore table " + tableName, "Please make sure the table exists."));
                            mainController.refreshActiveTab();
                            if (count > 0) Platform.runLater(() -> DialogUtils.showInfoDialog("Success", "Table " + tableName + " successfully restored", ""));
                        } catch (DatabaseException e) {
                            Platform.runLater(() -> DialogUtils.showErrorDialog("Error", "Failed to restore table " + tableName, e.getMessage()));
                            log.error("Failed to restore table", e);
                        }
                    });
                });
    }

}
