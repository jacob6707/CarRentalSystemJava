@SuppressWarnings("module")
module dev.jacob6707.carrentalsystemjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.json.bind;
    requires org.slf4j;
    requires java.desktop;

    exports dev.jacob6707.carrentalsystemjavafx.controller;
    opens dev.jacob6707.carrentalsystemjavafx.controller to javafx.fxml;
    exports dev.jacob6707.carrentalsystemjavafx.app;
    opens dev.jacob6707.carrentalsystemjavafx.app to javafx.fxml;
    exports dev.jacob6707.carrentalsystemjavafx.model;
    opens dev.jacob6707.carrentalsystemjavafx.model to jakarta.json.bind, org.eclipse.yasson, javafx.fxml;
    exports dev.jacob6707.carrentalsystemjavafx.model.vehicle;
    opens dev.jacob6707.carrentalsystemjavafx.model.vehicle to jakarta.json.bind, org.eclipse.yasson, javafx.fxml;
    exports dev.jacob6707.carrentalsystemjavafx.model.person;
    opens dev.jacob6707.carrentalsystemjavafx.model.person to jakarta.json.bind, org.eclipse.yasson, javafx.fxml;
    exports dev.jacob6707.carrentalsystemjavafx.model.rental;
    opens dev.jacob6707.carrentalsystemjavafx.model.rental to jakarta.json.bind, org.eclipse.yasson, javafx.fxml;
    exports dev.jacob6707.carrentalsystemjavafx.util;
    opens dev.jacob6707.carrentalsystemjavafx.util to jakarta.json.bind, org.eclipse.yasson, javafx.fxml;
    exports dev.jacob6707.carrentalsystemjavafx.exception;
    opens dev.jacob6707.carrentalsystemjavafx.exception to jakarta.json.bind, org.eclipse.yasson, javafx.fxml;
    exports dev.jacob6707.carrentalsystemjavafx.repository;
    opens dev.jacob6707.carrentalsystemjavafx.repository to jakarta.json.bind, org.eclipse.yasson, javafx.fxml;

}