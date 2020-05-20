package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFileChooser;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Configuration_2 extends Application{
	
	ObservableList<Information> data=FXCollections.observableArrayList();
	ArrayList<String> saveVehicle=new ArrayList<String>();
	ArrayList<String> saveAge=new ArrayList<String>();
	ArrayList<String> saveGender=new ArrayList<String>();
	ArrayList<Traffic> traffic_collection = new ArrayList<Traffic>();
	
	public Configuration_2() {
		
	}
	
	public Configuration_2(ArrayList<Traffic> traffic) {
		this.traffic_collection = traffic;
		String gender = null;
		for (int i = 0; i < this.traffic_collection.size(); i++) {
			Traffic object = traffic_collection.get(i);
			
			if(object.is_Male) {
				gender = "Male";
			}else {
				gender = "Female";
			}
			
			if (object.V_type=="Car") {				
				data.add(new Information("Car"+","+((Car) object).getType(), Integer.toString(object.age), gender));
			}else {
				data.add(new Information(object.V_type, Integer.toString(object.age),gender));
			}
		}
	}
	

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
			
			Label Car_Type = new Label("Car type:");
			grid.add(Car_Type, 0, 0);
			ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("Car", "Truck", "Bus"));
			cb.setVisible(true);
			grid.add(cb, 1, 0);
			
//			The car type only shows up when the car toggle button is selected
			tb1.setOnAction((new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					cb.setVisible(true);
					Car_Type.setVisible(true);
				}
			}));
			tb2.setOnAction((new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					cb.setVisible(false);
					Car_Type.setVisible(false);
				}
			}));
			tb3.setOnAction((new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					cb.setVisible(false);
					Car_Type.setVisible(false);
				}
			}));
			
			
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
	        		Vehicle = "Car"+","+cb.getValue();
	        		traffic = new Car((String)cb.getValue(), is_Male, Integer.parseInt(AgeTextField.getText()));
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
	        	JFileChooser ch=new JFileChooser();
	        	int res=ch.showSaveDialog(null);
	        	if(res==JFileChooser.APPROVE_OPTION) {
	        		File file=ch.getSelectedFile();
	        		file=new File(file.getPath()+".txt");
	        	
	        	FileWriter rr;
				try {
					rr = new FileWriter(file);
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
	        	}
	        });
	        
	        ArrayList<Traffic> list2 = new ArrayList<>();
	        simulate_button.setOnAction(e->{
	        int ii=table.getItems().size();
	        	for(int ty=0;ty<ii;ty++) {
	        		int age=Integer.parseInt(value(2,table,ty));
	        		if((!value(0,table,ty).equals("Cyclist"))&&(!value(0,table,ty).equals("Walker"))) {
	        			if(value(1,table,ty).equals("Male")) {
	        				
	        				list2.add(new Car(value(0,table,ty).substring(4),true,age));
	        			}
	        			else {
	        				list2.add(new Car(value(0,table,ty).substring(4),false,age));
	        			}
	        		
	        		}
	        		else if(value(0,table,ty).equals("Walker")) {
	        			if(value(1,table,ty).equals("Male")) {
	        				list2.add(new Walker(true,age));
	        			}
	        			else {
	        				list2.add(new Walker(false,age));
	        			}
	        		}
	        		else {
	        			if(value(1,table,ty).equals("Male")) {
	        				list2.add(new Cyclist(true,age));
	        			}
	        			else {
	        				list2.add(new Cyclist(false,age));
	        			}
	        		}
	        	}
				Simulation openn=new Simulation(list2);
				try
				{
					openn.start_simulate();
					primaryStage.hide();
					 ArrayList<ArrayList<Record>> result = openn.getFullRecord();
				        for(int i=0;i<result.size();i++){
				            System.out.println("round "+i+" :");
				            for(Record r:result.get(i)){
				                System.out.println("     "+r);
				            }
				        }
				        
				        VBox roott = new VBox();
						
						ListView intersection = new ListView();
						
						//ArrayList<ArrayList<Record>> records = new ArrayList<ArrayList<Record>>();
						//records = getIntersections();
						int total_clashes = 0;
						for (int i = 0; i < result.size()-1; i++) {
							ArrayList<Record> turn = result.get(i);
							ArrayList<Record> turn2 = result.get(i+1);
							for (int j = 0; j < turn.size(); j++) {
								Record item = turn.get(j);
								if (item.action.equals("meet")) {
									GridPane cell = new GridPane();
									Text t1 = new Text("Traffic1: ");
									Text t2 = new Text("Traffic2: ");
									Text traffic1 = new Text(item.selftype);
									Text traffic2 = new Text(item.targettype);
									Text action = new Text("Action");
									Text traffic1_action = new Text();
									Text traffic2_action = new Text();
									Text traffic1_gender;
									Text traffic2_gender;
									
									String traffic1_age = Integer.toString(item.age1);
									String traffic2_age = Integer.toString(item.age2);
									
									
									Boolean crash = false;
									for (int n=0; n < turn.size(); n++) {
										Record temp_record = turn.get(n);
										if (temp_record.action.equals("pass")) {
											if (temp_record.selfname==item.selfname) {
												traffic1_action.setText(" "+temp_record.action);
											}
											if(temp_record.selfname==item.targetname) {
												traffic2_action.setText(" "+temp_record.action);
											}
										}
										else if(temp_record.action.equals("yield")) {
											if (temp_record.selfname==item.selfname) {
												traffic1_action.setText(" "+temp_record.action);
											}
											if(temp_record.selfname==item.targetname) {
												traffic2_action.setText(" "+temp_record.action);
											}
										}//else if(temp_record.action.equals("crash")) {
											//crash = true;
									//	}
									}
									if(item.is_male1) {
										traffic1=new Text(item.selftype+" "+"male"+" "+traffic1_age);
									}
									else {
										traffic1_gender = new Text(item.selftype+" "+"female"+" "+traffic1_age);
									}
									if(item.is_male2) {
										traffic2=new Text(item.targettype+" "+"male"+" "+traffic2_age);
										}
									else {
										traffic2=new Text(item.targettype+" "+"female"+" "+traffic2_age);
										}
									
									for(int m=0;m<turn2.size();m++) {
										Record cc=turn2.get(m);
										if(cc.action.equals("crash")) {
											crash = true;
										}
									}
									
									
									
									Text outcome = new Text();
									if (crash) {
										outcome.setText("Outcome: crashed");
										total_clashes++;
									}else {
										outcome.setText("Outcome: safe pass");
									}
									//total_clashes++;
									cell.add(t1, 0, 1);
									cell.add(t2, 0, 2);
									cell.add(traffic1, 1, 1);
									cell.add(traffic2, 1, 2);
									cell.add(action, 2, 0);
									cell.add(traffic2_action, 2, 1);
									cell.add(traffic1_action, 2, 2);
									cell.add(outcome, 0, 3, 2, 1);
									intersection.getItems().add(cell);
								}
								
							}
//							GridPane cell = new GridPane();
//							Text t1 = new Text("Traffic1: ");
//							Text t2 = new Text("Traffic2: ");
//							Text traffic1 = new Text(item.getTraffic1().V_type);
//							Text traffic2 = new Text(item.getTraffic2().V_type);
//							Text action = new Text("Action");
//							Text traffic1_action = new Text(item.getTraffic1_move().toString());
//							Text traffic2_action = new Text(item.getTraffic2_move().toString());
//							Text outcome = new Text("Outcome: "+item.getResult());
//							cell.add(t1, 0, 1);
//							cell.add(t2, 0, 2);
//							cell.add(traffic1, 1, 1);
//							cell.add(traffic2, 1, 2);
//							cell.add(action, 2, 0);
//							cell.add(traffic2_action, 2, 1);
//							cell.add(traffic1_action, 2, 2);
//							cell.add(outcome, 0, 3, 2, 1);
//							intersection.getItems().add(cell);
						}
						
						roott.getChildren().add(intersection);
						
						
						Text total_clash = new Text("Number of crashes: "+total_clashes);
						roott.getChildren().add(total_clash);
						
						Scene scenee = new Scene(roott,400,400);
						primaryStage.setScene(scenee);
						primaryStage.show();
						 
				        
				        
				}
				catch(Exception e1) {
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
