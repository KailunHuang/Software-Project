package roadSimulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Welcomepage2 extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		Button newSimulation=new Button("New Simulation");
		Button load=new Button("Load Simulation File");
		newSimulation.setTranslateX(10);
		newSimulation.setTranslateY(-30);
		newSimulation.setPrefWidth(300);
		newSimulation.setPrefHeight(50);
		load.setTranslateX(10);
    	load.setTranslateY(70);
		load.setPrefWidth(300);
		load.setPrefHeight(50);
		newSimulation.setOnAction(e->{
			Configuration open=new Configuration();
			try
			{
				open.start(new Stage());
				arg0.hide();
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
		});
		load.setOnAction(e->{
			Overview open=new Overview();
			try
			{
				open.start(new Stage());
				arg0.hide();
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
		});
		StackPane page=new StackPane();
		page.getChildren().add(newSimulation);
		page.getChildren().add(load);
		Scene welpage=new Scene(page,1500,900);
		arg0.setScene(welpage);
		arg0.show();
	}

}
