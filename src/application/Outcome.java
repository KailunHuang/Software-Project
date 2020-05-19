package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Outcome extends Application{
	
	private ArrayList<ArrayList<Record>> records = new ArrayList<ArrayList<Record>>();
	
	public Outcome(ArrayList<ArrayList<Record>> records) {
		this.records = records;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox root = new VBox();
		
		ListView intersection = new ListView();
		
//		records = getIntersections();
		int total_clashes = 0;
		for (int i = 0; i < records.size(); i++) {
			ArrayList<Record> turn = records.get(i);
			for (int j = 0; j < turn.size(); j++) {
				Record item = turn.get(j);
				if (item.action=="meet") {
					GridPane cell = new GridPane();
					Text t1 = new Text("Traffic1: ");
					Text t2 = new Text("Traffic2: ");
					Text traffic1 = new Text(item.selftype);
					Text traffic2 = new Text(item.targettype);
					Text action = new Text("Action");
					Text traffic1_action = new Text();
					Text traffic2_action = new Text();
					Boolean crash = false;
					for (int n=0; n < turn.size(); n++) {
						Record temp_record = turn.get(n);
						if (temp_record.action.equals("pass")) {
							if (temp_record.selfname==item.selfname) {
								traffic1_action.setText(temp_record.action);
							}else {
								traffic2_action.setText(temp_record.action);
							}
						}else if(temp_record.action.equals("stop")) {
							if (temp_record.selfname==item.selfname) {
								traffic1_action.setText(temp_record.action);
							}else {
								traffic2_action.setText(temp_record.action);
							}
						}else if(temp_record.action.equals("crash")) {
							crash = true;
						}
					}
					
					Text outcome = new Text();
					if (crash) {
						outcome.setText("Outcome: crashed");
					}else {
						outcome.setText("Outcome: safe pass");
					}
					total_clashes++;
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

		}
		
		root.getChildren().add(intersection);
		
		
		Text total_clash = new Text("Number of clashes: "+total_clashes);
		root.getChildren().add(total_clash);
		
		Scene scene = new Scene(root,400,400);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	
	
	private int getTotalClashes() {
		// TODO Auto-generated method stub
		return 8;
	}



//	private ArrayList<ArrayList<Record>> getIntersections() {
//		// TODO Auto-generated method stub
//		Testoutput test = new Testoutput();
//		return test.fullrecords;
//	}



	public static void main(String[] args) {
		launch(args);
	}
	
}