package dev.jacob6707.carrentalsystemjavafx.app;

import javafx.application.Application;

/**
 * Entry point for the JavaFX application.
 */
public class CarRentalSystemJavaFXLauncher {
    private CarRentalSystemJavaFXLauncher() {}

    /**
     * Launches the JavaFX application.
     * @param args Command line arguments
     */
    static void main(String[] args) {
        Application.launch(CarRentalSystemJavaFXApp.class, args);
    }
}
