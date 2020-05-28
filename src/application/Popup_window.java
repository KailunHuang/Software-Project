package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class Popup_window{

    private String alert_content;

    public Popup_window(String Type){
        if (Type.equals("Car")){
            alert_content = "The driver's age should be between 18 and 100";
        }else{
            alert_content = "The pedestrian's age should be between 8 and 100";
        }
    }


    public void start() throws Exception {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Age Alert");
        HBox hbox = new HBox();
        Label label = new Label(alert_content);
        hbox.getChildren().add(label);
        Scene scene = new Scene(hbox, 400, 100);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }
}
