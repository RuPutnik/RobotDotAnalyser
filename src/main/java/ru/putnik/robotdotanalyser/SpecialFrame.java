package ru.putnik.robotdotanalyser;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SpecialFrame extends Application implements Initializable {
    private MouseFeatures features=new MouseFeatures();
    @FXML
    private Button startPrint;
    @FXML
    private Button moveCursor;
    @FXML
    private TextField coordinatesField;
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

    public int[] getCoordinates(){
        int[] coordinates=new int[2];
        String coordsLine=coordinatesField.getText();
        if(coordsLine.matches("\\d+:\\d+")) {
            coordinates[0]=Integer.parseInt(coordsLine.split(":")[0]);
            coordinates[1]=Integer.parseInt(coordsLine.split(":")[1]);
        }else {
            Alert errorAlert=new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Координаты были введены неверно!");
            errorAlert.setTitle("Ошибка");
            errorAlert.setHeaderText("Ошибка определения координат");
            errorAlert.show();
            coordinatesField.setText("0:0");
            coordinates[0]=0;
            coordinates[1]=0;
        }
        return coordinates;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startPrint.setOnAction(new StartPrintCoordinates());
        moveCursor.setOnAction(new MoveCursorToCoordinates());
    }
    public class StartPrintCoordinates implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            features.printCoordinates();
        }
    }
    public class MoveCursorToCoordinates implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            if(!coordinatesField.getText().equals("")) {
                int[] coordinates = getCoordinates();
                features.moveCursor(coordinates[0], coordinates[1]);
            }else {
                Alert errorAlert=new Alert(Alert.AlertType.WARNING);
                errorAlert.setHeaderText("Поле для координат пусто");
                errorAlert.setTitle("Ошибка");
                errorAlert.setContentText("Для переноса курсора нужно указать координаты!");
                errorAlert.show();
            }
        }
    }

}
