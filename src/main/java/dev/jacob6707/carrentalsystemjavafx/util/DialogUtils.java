package dev.jacob6707.carrentalsystemjavafx.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DialogUtils {
    private DialogUtils() {}

    private static Optional<ButtonType> showDialog(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    public static void showErrorDialog(String title, String header, String content) {
        showDialog(Alert.AlertType.ERROR, title, header, content);
    }

    public static void showWarningDialog(String title, String header, String content) {
        showDialog(Alert.AlertType.WARNING, title, header, content);
    }

    public static void showInfoDialog(String title, String header, String content) {
        showDialog(Alert.AlertType.INFORMATION, title, header, content);
    }

    public static Optional<ButtonType> showConfirmationDialog(String title, String header, String content) {
        return showDialog(Alert.AlertType.CONFIRMATION, title, header, content);
    }


}
