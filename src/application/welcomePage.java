package application;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.Label;
import java.net.URL;

public class welcomePage extends Application{
	
	@FXML public Label label1;
	
	@FXML public Button button1;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//    	Parent root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
    	VBox root = (VBox) FXMLLoader.load(getClass().getResource("Welcome.fxml"));
    	
        Scene scene = new Scene(root);
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}