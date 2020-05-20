package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
public class WelcomePage3 extends Application {
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
			Configuration_2 open=new Configuration_2();
			try
			{
				open.start(new Stage());
				arg0.hide();
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
		});
		
		load.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	ArrayList<Traffic> collections = new ArrayList<Traffic>();
		    	try {
					collections = readCsvFile();
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		    	
				Configuration_2 open=new Configuration_2(collections);
				try
				{
					open.start(new Stage());
					arg0.hide();
				}
				catch(Exception e1) {
					e1.printStackTrace();
				}
		    }
		});
		
		StackPane page=new StackPane();
		page.getChildren().add(newSimulation);
		page.getChildren().add(load);
		Scene welpage=new Scene(page,500,400);
		arg0.setScene(welpage);
		arg0.show();
	}
	
	public ArrayList<Traffic> readCsvFile() throws FileNotFoundException{
		File filename = chooseOpenFile();
		ArrayList<Traffic> collections_ = new ArrayList<Traffic>();
		Boolean is_Male = null;
		Scanner myReader = new Scanner(filename);
	    while (myReader.hasNextLine()) {
	        String[] data = myReader.nextLine().split(",");
	        if (data.length > 3) {
	        	if(data[2].equals("Male")) {
	        		is_Male = true;
	        	}else {
	        		is_Male = false;
	        	}
	        	collections_.add(new Car(data[1],is_Male,Integer.parseInt(data[3])));
//	        otherwise it's a walker or cyclist object	
	        }else {
	        	if(data[1].equals("Male")){
	        		is_Male = true;
	        	}else {
	        		is_Male = false;
	        	}
	        	if(data[0].equals("Walker")) {
	        		collections_.add(new Walker(is_Male, Integer.parseInt(data[2])));
	        	}else {
	        		System.out.println(data[0]);
	        		collections_.add(new Cyclist(is_Male, Integer.parseInt(data[2])));
	        	}
	        }
	        	
	        System.out.println(data);
	        
	    }
		System.out.println(collections_.size());
		return collections_;
		
	}
	
	public File chooseOpenFile() {
        File file = null;
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        return file;
    }
	
}
