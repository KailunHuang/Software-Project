package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Configuration_2 extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		try {

			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,800,400);
			
			//**********left side of UI*************
			
			// We need 3 horizontal buttons for the users to
			final ToggleGroup group = new ToggleGroup();
			
			ToggleButton tb1 = new ToggleButton("Car");
			tb1.setToggleGroup(group);
			tb1.setSelected(true);
			
			ToggleButton tb2 = new ToggleButton("Foot passger");
			tb2.setToggleGroup(group);
			
			ToggleButton tb3 = new ToggleButton("Cyclist");
			tb3.setToggleGroup(group);
			
			HBox hbox = new HBox(tb1, tb2, tb3);
			hbox.setPadding(new Insets(0,0,0,25));
			
			// The text input sections
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 50, 25));
			
			Label CarType = new Label("Car type:");
			grid.add(CarType, 0, 0);
			TextField CarTextField = new TextField();
			grid.add(CarTextField, 1, 0);
			
			Label Age = new Label("Age:");
			grid.add(Age, 0, 1);
			TextField AgeTextField = new TextField();
			grid.add(AgeTextField, 1, 1);
			
			Label Gender = new Label("Gender:");
			grid.add(Gender, 0, 2);
			
			final ToggleGroup group2 = new ToggleGroup();
			RadioButton rb1 = new RadioButton("Male");
			rb1.setPadding(new Insets(0,10,0,0));
			rb1.setToggleGroup(group2);
			rb1.setSelected(true);
;
			RadioButton rb2 = new RadioButton("Female");
			rb2.setPadding(new Insets(0,10,0,0));
			rb2.setToggleGroup(group2);

			HBox gender_button = new HBox(rb1, rb2);
			grid.add(gender_button, 1, 2);
			
			//save and input buttons 
			Button save_button = new Button("Save");
			Button add_button = new Button("Add");
			
			VBox save_add = new VBox(save_button, add_button);
			save_add.setPadding(new Insets(25,25,25,25));
			
			VBox addBox = new VBox(hbox, grid, save_add);
			addBox.setPadding(new Insets(15,15,15,15));
		
			
			//***************************************
			
			
			//**********right side of UI*************
			final TableView table = new TableView();
			table.setEditable(true);
	        table.setPrefSize(400, 400);
	        
	        TableColumn TrafficCol = new TableColumn("Traffic type");
	        TrafficCol.setMinWidth(200);
	        TableColumn GenderCol = new TableColumn("Gender");
	        TableColumn AgeCol = new TableColumn("Age");
	        
	        table.getColumns().addAll(TrafficCol, GenderCol, AgeCol);
	        
			VBox viewBox = new VBox(table);
			viewBox.setPadding(new Insets(15,15,15,15));
			//***************************************
			
			root.setLeft(addBox);
			root.setRight(viewBox);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public static void main(String[] args) {
		launch(args);
	}
	
}
