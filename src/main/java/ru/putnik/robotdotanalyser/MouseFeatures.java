package ru.putnik.robotdotanalyser;

import javafx.scene.control.Alert;

import java.awt.*;

public class MouseFeatures {
    private Robot robot;
    public MouseFeatures(){
        try {
            robot=new Robot();
        } catch (AWTException e) {
            Alert errorAlert=new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Инифиализация ядра");
            errorAlert.setTitle("Ошибка");
            errorAlert.setContentText("Во время инициализации произошла ошибка!");
            errorAlert.show();
            e.printStackTrace();
        }
    }
    public int[] printCoordinates(){
        int x=0;
        int y=0;

        Point point = MouseInfo.getPointerInfo().getLocation();
        x = point.x;
        y = point.y;

        return new int[]{x,y};
    }
    public void moveCursor(int x,int y){
        robot.mouseMove(x,y);
    }
}
