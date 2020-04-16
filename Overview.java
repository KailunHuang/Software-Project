package roadSimulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Overview extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
        Button newSimulation=new Button("ew");
		
		newSimulation.setTranslateX(10);
		newSimulation.setTranslateY(-30);
		newSimulation.setPrefWidth(300);
		newSimulation.setPrefHeight(50);
		StackPane page=new StackPane();
		page.getChildren().add(newSimulation);
		Scene welpage=new Scene(page,1500,900);
		primaryStage.setScene(welpage);
		primaryStage.show();
	}

}
