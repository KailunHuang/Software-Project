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

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		VBox root = new VBox();
		
		ListView intersection = new ListView();
		
		ArrayList<Intersection> intersections = new ArrayList<Intersection>();
		intersections = getIntersections();
		
		for (int i = 0; i < intersections.size(); i++) {
			Intersection item = intersections.get(i);
			GridPane cell = new GridPane();
			Text t1 = new Text("Traffic1");
			Text t2 = new Text("Traffic2");
			Text traffic1 = new Text(item.getTraffic1().V_type);
			Text traffic2 = new Text(item.getTraffic2().V_type);
			Text action = new Text("Action");
			Text traffic1_action = new Text(item.getTraffic1_move().toString());
			Text traffic2_action = new Text(item.getTraffic2_move().toString());
			Text outcome = new Text("Outcome: "+item.getResult());
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
		
		root.getChildren().add(intersection);
		
		int n = getTotalClashes();
		Text total_clash = new Text("Number of clashes: "+n);
		root.getChildren().add(total_clash);
		
		Scene scene = new Scene(root,400,400);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	
	
	private int getTotalClashes() {
		// TODO Auto-generated method stub
		return 8;
	}



	private ArrayList<Intersection> getIntersections() {
		// TODO Auto-generated method stub
		ArrayList<Intersection> intersections = new ArrayList<Intersection>();
		Car car = new Car("Car", false, 30);
		for (int i = 0; i < 10; i ++) {
			Intersection intersection = new Intersection(car, car, Intersection.Move.YIELD, 
					Intersection.Move.MOVE, Intersection.Result.PASS);
			intersections.add(intersection);
		}
		return intersections;
	}



	public static void main(String[] args) {
		launch(args);
	}
	
}
