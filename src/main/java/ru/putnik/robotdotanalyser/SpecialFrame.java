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
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpecialFrame extends Application implements Initializable {
    private MouseFeatures features=new MouseFeatures();
    static private ExecutorService ex;
    private Stage stage;
    static private boolean print;
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
        stage=primaryStage;
        Parent parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/SpecialFrame.fxml")));
        stage.setScene(new Scene(parent));

        stage.setResizable(false);
        stage.setWidth(250);
        stage.setHeight(150);
        stage.setTitle("Координаты курсора");
        stage.show();

        stage.setOnCloseRequest(new ShutdownThreadPrint());
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
            print=true;
            ex=Executors.newSingleThreadExecutor();
            ex.submit(()->{
                while (print) {
                    int[] coordsCursor = features.printCoordinates();
                    coordinatesField.setText(coordsCursor[0] + ":" + coordsCursor[1]);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            ex.shutdown();
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
    public class ShutdownThreadPrint implements EventHandler<WindowEvent>{
        @Override
        public void handle(WindowEvent event) {
                print=false;
            }
    }
}
