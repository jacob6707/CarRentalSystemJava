package dev.jacob6707.carrentalsystemjavafx.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Utility class for handling dialogs.
 */
public class DialogUtils {
    private DialogUtils() {}

    /**
     * Shows a dialog with the specified parameters.
     * @param alertType The type of alert to show
     * @param title The title of the alert
     * @param header The header text of the alert
     * @param content The content text of the alert
     * @return The result of the dialog
     */
    private static Optional<ButtonType> showDialog(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    /**
     * Shows an error dialog.
     * @param title The title of the dialog
     * @param header The header text of the dialog
     * @param content The content text of the dialog
     */
    public static void showErrorDialog(String title, String header, String content) {
        showDialog(Alert.AlertType.ERROR, title, header, content);
    }

    /**
     * Shows a warning dialog.
     * @param title The title of the dialog
     * @param header The header text of the dialog
     * @param content The content text of the dialog
     */
    public static void showWarningDialog(String title, String header, String content) {
        showDialog(Alert.AlertType.WARNING, title, header, content);
    }

    /**
     * Shows an information dialog.
     * @param title The title of the dialog
     * @param header The header text of the dialog
     * @param content The content text of the dialog
     */
    public static void showInfoDialog(String title, String header, String content) {
        showDialog(Alert.AlertType.INFORMATION, title, header, content);
    }

    /**
     * Shows a confirmation dialog.
     * @param title The title of the dialog
     * @param header The header text of the dialog
     * @param content The content text of the dialog
     * @return The result of the dialog
     */
    public static Optional<ButtonType> showConfirmationDialog(String title, String header, String content) {
        return showDialog(Alert.AlertType.CONFIRMATION, title, header, content);
    }


}
