package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	
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
			
			ArrayList<Control> togbutton_controls = new ArrayList<Control>();
			togbutton_controls.add(tb1);
			togbutton_controls.add(tb2);
			togbutton_controls.add(tb3);
			HBox hbox = addHBox(togbutton_controls);
			
			// The text input sections
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			
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
			ArrayList<Control> gender_rb = new ArrayList<Control>();
			RadioButton rb1 = new RadioButton("Male");
			rb1.setPadding(new Insets(0,10,0,0));
			rb1.setToggleGroup(group2);
			rb1.setSelected(true);
			gender_rb.add(rb1);
			RadioButton rb2 = new RadioButton("Female");
			rb2.setPadding(new Insets(0,10,0,0));
			rb2.setToggleGroup(group2);
			gender_rb.add(rb2);
			HBox gender_button = addHBox(gender_rb);
			grid.add(gender_button, 1, 2);
			
			//save and input buttons 
			Button save_button = new Button("save");
			Button add_button = new Button("Button");
			
			
			VBox addBox = vLayoutBox(hbox);
			addBox.getChildren().addAll(grid, save_button, add_button);
		
			
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
	        
	        
	        ArrayList<Control> view_controls = new ArrayList<Control>();
	        view_controls.add(table);
	        
			VBox viewBox = addVBox(view_controls);
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
	
	public VBox addVBox(ArrayList<Control> togbutton_controls) {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		
		
		for (int i = 0; i < togbutton_controls.size(); i++) {
			vbox.getChildren().add(togbutton_controls.get(i));
		}
		
		return vbox;
		
	}
	
	public VBox vLayoutBox(Pane pane) {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
        
        vbox.getChildren().add(pane);
		return vbox;
		
	}
	
	public HBox addHBox(ArrayList<Control> togbutton_controls) {
		HBox hbox = new HBox();
		
		for (int i = 0; i < togbutton_controls.size(); i++) {
			hbox.getChildren().add(togbutton_controls.get(i));
		}
		return hbox;
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

