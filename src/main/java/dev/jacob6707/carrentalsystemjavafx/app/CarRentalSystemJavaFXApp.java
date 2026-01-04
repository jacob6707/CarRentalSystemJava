package dev.jacob6707.carrentalsystemjavafx.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class CarRentalSystemJavaFXApp extends Application {


    /**
     * Initializes the primary stage with a configured scene
     * @param stage Primary stage
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarRentalSystemJavaFXApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);
        stage.setTitle("Car Rental System");
        stage.setScene(scene);
        stage.show();
    }
}
