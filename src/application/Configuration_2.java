package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Configuration_2 extends Application{
	
	ObservableList<Information> data=FXCollections.observableArrayList();
	ArrayList<String> saveVehicle=new ArrayList<String>();
	ArrayList<String> saveAge=new ArrayList<String>();
	ArrayList<String> saveGender=new ArrayList<String>();
	ArrayList<Traffic> traffic_collection = new ArrayList<Traffic>();
	//@Override
	public static String value(int k, TableView t, int m) {
		TableColumn Column = (TableColumn) t.getColumns().get(k);
 	   ObservableValue observeValue = Column.getCellObservableValue(m);
 	   String a=observeValue.getValue().toString();
 	   return a;
	}
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
			Button simulate_button = new Button("Start simulate");
			VBox save_add = new VBox(save_button, add_button, simulate_button);
			save_add.setSpacing(5);
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
	        
	        TrafficCol.setCellValueFactory(
	        		new PropertyValueFactory<>("cartype"));
	        GenderCol.setCellValueFactory(
	        		new PropertyValueFactory<>("gender"));
	        AgeCol.setCellValueFactory(
	        		new PropertyValueFactory<>("age"));
	        table.setItems(data);
	        
	        add_button.setOnAction((ActionEvent e)->{
	        	String Gend=null;
	        	Boolean is_Male = null;
	        	String Vehicle = null;
	        	Traffic traffic = null;
	        	
	        	if(rb1.isSelected()) {
	        		Gend="Male";
	        		is_Male = true;
	        	}
	        	else {
	        		Gend="Female";
	        		is_Male = false;
	        	}
	        	
	        	if (tb1.isSelected()) {
	        		Vehicle = "Car"+","+CarTextField.getText();
	        		traffic = new Car(CarTextField.getText(), is_Male, Integer.parseInt(AgeTextField.getText()));
	        	}else if(tb2.isSelected()) {
	        		Vehicle = "Walker";
	        		traffic = new Walker(is_Male, Integer.parseInt(AgeTextField.getText()));
	        	}else {
	        		Vehicle="Cyclist";
	        		traffic = new Cyclist(is_Male, Integer.parseInt(AgeTextField.getText()));
	        	}
	        	
	        	data.add(new Information(
	        			Vehicle,
	        			AgeTextField.getText(),
	        			Gend));
	        	traffic_collection.add(traffic);
	     //   	System.out.print(traffic_collection);
	        });
	       // ObservableValue observableValue = tableColumn.getCellObservableValue(0);
	  
	        save_button.setOnAction((ActionEvent e)->{
	        	int i=table.getItems().size();
	        	for(int row=0;row<i;row++) {
	        	 saveVehicle.add(value(0,table,row));
	        	 saveAge.add(value(1,table,row));
	        	 saveGender.add(value(2,table,row));
	        	}
	        	File file=new File("E:/number.txt");
	        	FileWriter rr;
				try {
					rr = new FileWriter("E:/number.txt");
					BufferedWriter bb= new BufferedWriter(rr);
		    		for(int size=0;size<i;size++) {
		    		bb.write(saveVehicle.get(size)+",");
		    		bb.write(saveAge.get(size)+",");
		    		bb.write(saveGender.get(size)+"\n");
		    		}
		    		bb.close();
		    		rr.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        });
	        
	        
	        
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
