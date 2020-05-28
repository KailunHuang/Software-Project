package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JFileChooser;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class Configuration_2 extends Application{

	public HashMap<String, HashMap<String, Double>> factor_map = new HashMap<>();
	ObservableList<Information> data=FXCollections.observableArrayList();
	ArrayList<String> saveVehicle=new ArrayList<String>();
	ArrayList<String> saveAge=new ArrayList<String>();
	ArrayList<String> saveGender=new ArrayList<String>();
	ArrayList<Traffic> traffic_collection = new ArrayList<Traffic>();
	static ArrayList<Double> StatisticalAge=new ArrayList<Double>();
	static ArrayList<Double> StatisticalAgeOfYield=new ArrayList<Double>();
	ArrayList StatisticalGender=new ArrayList();
	ArrayList StatisticalGenderYield=new ArrayList();
	
	static double under30=0;
	static double from30to45=0;
	static double from45to60=0;
	static double over60=0;
	static double Yunder30=0;
	static double Yfrom30to45=0;
	static double Yfrom45to60=0;
	static double Yover60=0;
	static double malecrash=0;
	static double femalecrash=0;
	static double maleyield=0;
	static double femaleyield=0;
	public Configuration_2() {
		initialFactors();
	}
	
	public static void sumAgeOfCrash(int age) {
		if(age<30) {
			under30++;
			
		}
		else if((age>=30)&&(age<45)) {
			from30to45++;
			
		}
		else if((age>=45)&&(age<=60)) {
			from45to60++;
			
		}
		else {
			over60++;
			
		}
	}
	
	public static void sumGenderOfCrash(boolean gender) {
		if(gender) {
			malecrash++;
		}
		else {
			femalecrash++;
		}
	}
	
	public static void sumGenderOfYield(boolean gender) {
		if(gender) {
			maleyield++;
		}
		else {
			femaleyield++;
		}
	}
	
	public static String round(int a) {
		double b=100*StatisticalAge.get(a);
		return String.format("%.2f", b)+"%";
	}
	
	public static String round2(int a) {
		double b=100*StatisticalAgeOfYield.get(a);
		return String.format("%.2f", b)+"%";
	}
	
	public static void sumAgeOfYield(int age) {
		if(age<30) {
			Yunder30++;
			
		}
		else if((age>=30)&&(age<45)) {
			Yfrom30to45++;
			
		}
		else if((age>=45)&&(age<=60)) {
			Yfrom45to60++;
			
		}
		else {
			Yover60++;
			
		}
	}
	
	
	public Configuration_2(ArrayList<Traffic> traffic) {
		initialFactors();
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
	
	
	
	public String GenderDefined(Boolean gender) {
    	if(gender) {
    		return "  male";
    	}
    	else {
    		return "  female";
    	}
    }
	
	public void start(Stage primaryStage) {
		try {

			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,850,600);
			
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
			hbox.setPadding(new Insets(0,0,5,25));

			// car type selector
			Label Car_Type = new Label("Car type: ");
			ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("Car", "Truck", "Bus"));
			cb.setVisible(true);
			HBox car_type = new HBox(Car_Type, cb);
			car_type.setPadding(new Insets(5,0,10,25));

			// setting factors
			GridPane factor_settings = new GridPane();
			factor_settings.setAlignment(Pos.CENTER);
			factor_settings.setHgap(10);
			factor_settings.setVgap(10);
			factor_settings.setPadding(new Insets(25, 25, 25, 25));
			factor_settings.setBorder(new Border(new BorderStroke(Color.BLACK,
					BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			factor_settings.setStyle("-fx-border-color: black");

			Label speed_factor = new Label("Speed factor: ");
			factor_settings.add(speed_factor, 0,0);
			TextField speed_text = new TextField("0.1");
			factor_settings.add(speed_text, 1, 0);
			Label crash_factor = new Label("Crash factor: ");
			factor_settings.add(crash_factor, 0, 1);
			TextField crash_text = new TextField("0.1");
			factor_settings.add(crash_text, 1, 1);
			Label stop_factor = new Label("Stop factor: ");
			factor_settings.add(stop_factor, 0, 2);
			TextField stop_text = new TextField("0.1");
			factor_settings.add(stop_text, 1, 2);
			Button submit = new Button("Submit factors");
			factor_settings.add(submit, 0, 3);

			// The text input sections
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			

			
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
					System.out.println(factor_map);
					speed_text.setText(factor_map.get("Walker").get("speed-factor").toString());
					stop_text.setText(factor_map.get("Walker").get("stop-factor").toString());
					crash_text.setText(factor_map.get("Walker").get("crash-factor").toString());
				}
			}));
			tb3.setOnAction((new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					cb.setVisible(false);
					Car_Type.setVisible(false);
					speed_text.setText(factor_map.get("Cyclist").get("speed-factor").toString());
					stop_text.setText(factor_map.get("Cyclist").get("stop-factor").toString());
					crash_text.setText(factor_map.get("Cyclist").get("crash-factor").toString());
				}
			}));

			submit.setOnAction((new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (tb1.isSelected()){
						if (cb.getValue().equals("Car")){
							factor_map.get("Car").put("speed-factor",Double.parseDouble(speed_text.getText()));
							factor_map.get("Car").put("crash-factor",Double.parseDouble(crash_text.getText()));
							factor_map.get("Car").put("stop-factor",Double.parseDouble(stop_text.getText()));
						}else if(cb.getValue().equals("Bus")){
							factor_map.get("Bus").put("speed-factor",Double.parseDouble(speed_text.getText()));
							factor_map.get("Bus").put("crash-factor",Double.parseDouble(crash_text.getText()));
							factor_map.get("Bus").put("stop-factor",Double.parseDouble(stop_text.getText()));
						}else if(cb.getValue().equals("Truck")){
							factor_map.get("Truck").put("speed-factor",Double.parseDouble(speed_text.getText()));
							factor_map.get("Truck").put("crash-factor",Double.parseDouble(crash_text.getText()));
							factor_map.get("Truck").put("stop-factor",Double.parseDouble(stop_text.getText()));
						}
					}else if(tb2.isSelected()){
						factor_map.get("Walker").put("speed-factor",Double.parseDouble(speed_text.getText()));
						factor_map.get("Walker").put("crash-factor",Double.parseDouble(crash_text.getText()));
						factor_map.get("Walker").put("stop-factor",Double.parseDouble(stop_text.getText()));
					}else if(tb3.isSelected()){
						factor_map.get("Cyclist").put("speed-factor",Double.parseDouble(speed_text.getText()));
						factor_map.get("Cyclist").put("crash-factor",Double.parseDouble(crash_text.getText()));
						factor_map.get("Cyclist").put("stop-factor",Double.parseDouble(stop_text.getText()));
					}
				}
			}));

			cb.getSelectionModel().selectedItemProperty().addListener((v, oldValues, newValue) -> {
				if (newValue.equals("Car")){
					speed_text.setText(factor_map.get("Car").get("speed-factor").toString());
					stop_text.setText(factor_map.get("Car").get("stop-factor").toString());
					crash_text.setText(factor_map.get("Car").get("crash-factor").toString());
				}else if(newValue.equals("Bus")){
					speed_text.setText(factor_map.get("Bus").get("speed-factor").toString());
					stop_text.setText(factor_map.get("Bus").get("stop-factor").toString());
					crash_text.setText(factor_map.get("Bus").get("crash-factor").toString());
				}else if(newValue.equals("Truck")){
					speed_text.setText(factor_map.get("Truck").get("speed-factor").toString());
					stop_text.setText(factor_map.get("Truck").get("stop-factor").toString());
					crash_text.setText(factor_map.get("Truck").get("crash-factor").toString());
				}
			});
			
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
			Button add_button = new Button("Add");
			grid.add(gender_button, 1, 2);
			grid.add(add_button, 0, 3);

			//save and input buttons
			Label density = new Label("Density: ");
			TextField density_text = new TextField("6");
			HBox density_box = new HBox(density, density_text);

			Button save_button = new Button("Save");
			Button simulate_button = new Button("Start simulate");
			VBox save_add = new VBox(density_box, save_button, simulate_button);
			save_add.setSpacing(5);
			save_add.setPadding(new Insets(25,25,25,25));
			
			VBox addBox = new VBox(hbox, car_type, factor_settings, grid, save_add);
			addBox.setPadding(new Insets(15,15,15,15));
		
			
			//***************************************
			
			
			//**********right side of UI*************
			final TableView table = new TableView();
			table.setEditable(true);
	        table.setPrefSize(400, 580);
	        
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


	        // Button actionListener
	        add_button.setOnAction((ActionEvent e)->{
	        	String Gend=null;
	        	Boolean is_Male = null;
	        	String Vehicle = null;
	        	Traffic traffic = null;

				int age = Integer.parseInt(AgeTextField.getText());
				if (tb1.isSelected() && (age < 18 || age > 100)){
					Popup_window popup = new Popup_window("Car");
					try {
						popup.start();
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}else if(tb2.isSelected() || tb3.isSelected()){
					if (age < 8 || age > 100){
						Popup_window popup = new Popup_window("Other");
						try {
							popup.start();
						} catch (Exception exception) {
							exception.printStackTrace();
						}
					}
				}else{
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
				}

	        });
	  
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
	        

	        simulate_button.setOnAction(e->{

				Simulation openn=new Simulation(traffic_collection);

				try
				{
					openn.setMax_involve_traffic(Integer.parseInt(density_text.getText()));
					openn.start_simulate();
					primaryStage.hide();
					ArrayList<ArrayList<Record>> result = openn.getFullRecord();
					ButtonClickSimulation animation_scene=new ButtonClickSimulation(result);
					animation_scene.start(new Stage());
				        for(int i=0;i<result.size();i++){
				            System.out.println("round "+i+" :");
				            for(Record r:result.get(i)){
				                System.out.println("     "+r);
				            }
				        }
				        
				        VBox roott = new VBox();
						
						ListView intersection = new ListView();
						
						double total_clashes = 0;
						Text crashAge=new Text(" ");
						Text YieldAge=new Text(" ");
						double meetyield=0;
						for (int i = 0; i < result.size()-1; i++) {
							int computePass=0;
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
									Text action = new Text(" Action ");
									Text age = new Text("Age ");
									Text gender = new Text(" Gender ");
									Text traffic1_action = new Text();
									Text traffic2_action = new Text();
									Text traffic1_gender;
									Text traffic2_gender;
									Text type=new Text("type");
									Text traffic1_age = new Text(Integer.toString(item.age1));
									Text traffic2_age = new Text(Integer.toString(item.age2));
									
									
									Boolean crash = false;
									for (int n=0; n < turn.size(); n++) {
										
										
										Record temp_record = turn.get(n);
										if (temp_record.action.equals("pass")) {
											if (temp_record.selfname==item.selfname) {
												traffic1_action.setText(" "+temp_record.action);
												computePass++;
											}
											if(temp_record.selfname==item.targetname) {
												traffic2_action.setText(" "+temp_record.action);
												computePass++;
											}
										}
										else if(temp_record.action.equals("yield")) {
											meetyield++;
											if (temp_record.selfname==item.selfname) {
												traffic1_action.setText(" "+temp_record.action);
												sumAgeOfYield(item.age1);
												sumGenderOfYield(item.is_male1);
											}
											if(temp_record.selfname==item.targetname) {
												traffic2_action.setText(" "+temp_record.action);
												sumAgeOfYield(item.age2);
												sumGenderOfYield(item.is_male2);
											}
										}//else if(temp_record.action.equals("crash")) {
											//crash = true;
									//	}
										
										
									}
									if(computePass==2) {
										sumAgeOfCrash(item.age1);
										sumAgeOfCrash(item.age2);
										sumGenderOfCrash(item.is_male1);
										sumGenderOfCrash(item.is_male2);
									}
									traffic1_gender=new Text(GenderDefined(item.is_male1));
									traffic2_gender=new Text(GenderDefined(item.is_male2));
									
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
									cell.add(type,1,0);
									cell.add(traffic1, 1, 1);
									cell.add(traffic2, 1, 2);
									cell.add(action, 4, 0);
									cell.add(age, 2, 0);
									cell.add(traffic1_age, 2, 1);
									cell.add(traffic2_age, 2, 2);
									cell.add(traffic1_gender, 3, 1);
									cell.add(traffic2_gender, 3, 2);
									cell.add(gender, 3, 0);
									cell.add(traffic2_action, 4, 1);
									cell.add(traffic1_action, 4, 2);
									cell.add(outcome, 0, 3, 2, 1);
									intersection.getItems().add(cell);
								}
								
							}
							
							//GridPane cell2 = new GridPane();
							//cell2.add(crashAge, 0, 1);
						//	cell2.add(YieldAge, 0, 2);
							//intersection.getItems().add(cell2);
						}
						StatisticalAge.add(under30*1.00/(total_clashes*2));
						StatisticalAge.add(from30to45*1.00/(total_clashes*2));
						StatisticalAge.add(from45to60*1.00/(total_clashes*2));
						double age60=1-StatisticalAge.get(0)-StatisticalAge.get(1)-StatisticalAge.get(2);
						StatisticalAge.add(age60);
						StatisticalAge.add(malecrash*1.00/(total_clashes*2));
						double fecrash=1-StatisticalAge.get(4);
						StatisticalAge.add(fecrash);
						
						StatisticalAgeOfYield.add(Yunder30*1.00/meetyield);
						StatisticalAgeOfYield.add(Yfrom30to45*1.00/meetyield);
						StatisticalAgeOfYield.add(Yfrom45to60*1.00/meetyield);
						double age60Y=1-StatisticalAgeOfYield.get(0)-StatisticalAgeOfYield.get(1)-StatisticalAgeOfYield.get(2);
						StatisticalAgeOfYield.add(age60Y);
						StatisticalAgeOfYield.add(maleyield*1.00/meetyield);
						double femaleyield2=1-StatisticalAgeOfYield.get(4);
						StatisticalAgeOfYield.add(femaleyield2);
						
						ArrayList<String> StaCrash=new ArrayList<String>();
						ArrayList<String> StaYield=new ArrayList<String>();
						for(int i=0;i<=5;i++) {
							StaCrash.add(round(i));
							StaYield.add(round2(i));
						}
						roott.getChildren().add(intersection);
						if((total_clashes!=0)&&(meetyield!=0)) {
						crashAge=new Text("Age distribution in the crashes is shown below: \n"+" under 30 years old: "+
						StaCrash.get(0)+"\n 30 to 45 years old: "+StaCrash.get(1)+"\n 45 to 60 years old: "+StaCrash.get(2)+"\n over 60 years old: "+
						StaCrash.get(3)+"\n");
						Text CrashGender=new Text("Gender distribution in the crashes is shown below: \n"+"male: "+
								StaCrash.get(4)+"\n"+"Female: "+StaCrash.get(5)+"\n");
						YieldAge=new Text("Age distribution of people who choose to yield is shown below: \n"+" under 30 years old: "+
						StaYield.get(0)+"\n 30 to 45 years old: "+StaYield.get(1)+"\n 45 to 60 years old: "+StaYield.get(2)+"\n over 60 years old: "+
						StaYield.get(3)+"\n");
						Text YieldGender=new Text("Gender distribution of people who choose to yield is shown below: \n"+"male: "+
								StaYield.get(4)+"\n"+"Female: "+StaYield.get(5));
						roott.getChildren().add(crashAge);
						roott.getChildren().add(CrashGender);
						roott.getChildren().add(YieldAge);
						roott.getChildren().add(YieldGender);
						}
						else {
							Text un=new Text("Statistical results are unavailable due to the lack of enough vehicles");
							roott.getChildren().add(un);
						}
						
					
						Text total_clash = new Text("\n"+"Number of crashes: "+total_clashes);
						
						roott.getChildren().add(total_clash);
						
						Scene scenee = new Scene(roott,650,800);
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


	public void initialFactors(){
		factor_map.put("Car", initialFactor());
		factor_map.put("Bus", initialFactor());
		factor_map.put("Truck", initialFactor());
		factor_map.put("Walker", initialFactor());
		factor_map.put("Cyclist", initialFactor());
	}

	public HashMap<String, Double> initialFactor(){
		HashMap<String, Double> current_factors = new HashMap<>();
		current_factors.put("speed-factor", 0.1);
		current_factors.put("crash-factor", 0.1);
		current_factors.put("stop-factor", 0.1);
		return current_factors;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
