package ru.putnik.robotdotanalyser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SpecialFrame extends Application implements Initializable {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/SpecialFrame.fxml")));
        primaryStage.setScene(new Scene(parent));

        primaryStage.setResizable(false);
        primaryStage.setWidth(250);
        primaryStage.setHeight(150);
        primaryStage.setTitle("Координаты курсора");
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
