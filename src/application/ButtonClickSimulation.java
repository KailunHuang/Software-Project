package application;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ButtonClickSimulation extends Application{
	
	private ArrayList<ArrayList<Record>> records = getRecords();
	private ArrayList<Shape> traffics = new ArrayList<Shape>();
	private HashMap<String, Shape> current_records = new HashMap<String, Shape>();
	private HashMap<String, Coordinate> future_records = new HashMap<String, Coordinate>();
	private int round = 0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Group root = new Group();
		int rows = 8;
		int columns = 11;
		GridPane gameGrid = new GridPane();
//		Draw Background Roads
	    for (int i = 0; i < columns; i++) {
	        ColumnConstraints columnn = new ColumnConstraints(60);
	        gameGrid.getColumnConstraints().add(columnn);

	    }
		
	    for (int i = 0; i < rows; i++) {
	        RowConstraints row = new RowConstraints(60);
	        gameGrid.getRowConstraints().add(row);
	    }
	    
	    gameGrid.setStyle("-fx-background-color: white; -fx-grid-lines-visible:true");
	    
	    loadRoadImage(gameGrid);
	    Button next = new Button("Next");
	    next.setMaxSize(60, 60);
	    next.setOnAction(new EventHandler() {

			@Override
			public void handle(Event event) {
				round++;
				ShowEachRoundAnimation(root);
				
			}
	    	
	    });
	    gameGrid.add(next, 0, 0);
	    root.getChildren().add(gameGrid);
	
	    ShowEachRoundAnimation(root);
	    
	    Scene scene = new Scene(root, columns*60, rows*60, Color.WHITE);
	    primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public static void loadRoadImage(GridPane gridpane) {
		int columns = 8;
		int rows = 11;
		
		Image image = new Image("application/img/road.png");
		ArrayList<ImageView> row= new ArrayList<ImageView>();
		ArrayList<ImageView> row2= new ArrayList<ImageView>();
		for (int i = 0; i < rows; i++) {
			ImageView imageView = new ImageView(image); 
			ImageView imageView2 = new ImageView(image);
			imageView.setFitWidth(60);
			imageView.setFitHeight(60);
			imageView2.setFitWidth(60);
			imageView2.setFitHeight(60);
			row.add(imageView);
			row2.add(imageView2);
		}
		
		ArrayList <ArrayList<ImageView>> column= new ArrayList<ArrayList<ImageView>>();
		
		
		for (int j = 0; j < 4; j++) {
			ArrayList<ImageView> views = new ArrayList<ImageView>();
			for (int i = 0; i < columns; i++) {
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(60);
				imageView.setFitHeight(60);
				views.add(imageView);
			}
			column.add(views);
		} 
		
		int[] column_nums = {2,3,7,8};
		
		for (int i=0; i < row.size(); i++) {
			gridpane.add(row.get(i), i, 3);
			gridpane.add(row2.get(i), i, 4);
		}
		
		for (int j=0; j < column.size(); j++) {
			int n = column_nums[j];
			for (int i=0; i < column.get(j).size(); i++) {
				gridpane.add(column.get(j).get(i), n, i);
			}
		}
	}
	
	public void ShowEachRoundPosition(GridPane gridpane) {
		ArrayList<Record> record_array = records.get(round);
//		clearTraffics(gridpane);
		traffics.clear();
		
		Shape shape;
		for (int i = 0; i < record_array.size(); i++) {
			Record record = record_array.get(i);
			if (record.action.equals("appear") || record.action.equals("move") || 
					record.action.equals("stop")) {
				if (record.selftype.equals("car")) {
					shape = new Circle(30);
					gridpane.add(shape, record.posx, record.posy);
					System.out.println(shape.getLayoutX());
					traffics.add(shape);
				}else if(record.selftype.equals("walker")) {
					shape = new Rectangle(30, 30);
					gridpane.add(shape, record.posx, record.posy);
					traffics.add(shape);
				}
			}
		}
	}
	
	public void ShowEachRoundAnimation(Group gridpane) {
		System.out.println(round);
		ArrayList<Record> round0 = records.get(round);
		ArrayList<Record> round1 = records.get(round+1);
		Shape shape;
		clearTraffics(gridpane);
		traffics.clear();
		current_records.clear();
		future_records.clear();
		
		for (int i = 0; i < round0.size(); i++) {
			Record record = round0.get(i);
			int x = record.posx*60+30;
			int y = record.posy*60+30;
			if (record.action.equals("appear") || record.action.equals("move") || 
					record.action.equals("stop")) {
				if (record.selftype.equals("car")) {
					shape = new Circle(30);
					shape.setLayoutX(x);
					shape.setLayoutY(y);
//					gridpane.add(shape, record.posx, record.posy);
					gridpane.getChildren().add(shape);
					traffics.add(shape);
					current_records.put(record.selfname, shape);
				}else if(record.selftype.equals("walker")) {
					shape = new Rectangle(30, 30);
					shape.setLayoutX(x);
					shape.setLayoutY(y);
//					gridpane.add(shape, record.posx, record.posy);
					traffics.add(shape);
					gridpane.getChildren().add(shape);
					current_records.put(record.selfname, shape);
				}
			}
		}
		
		for (int i = 0; i < round1.size(); i++) {
			Record record = round1.get(i);
			if (record.action.equals("appear") || record.action.equals("move") ||
					record.action.equals("stop")) {
				Coordinate coordinate = extractCoordinate(record);
				future_records.put(record.selfname, coordinate);
			}
		}	
		
		for (String i : current_records.keySet()) {
			if (future_records.get(i) != null) {
				int currentx = (int) current_records.get(i).getLayoutX();
				int currenty = (int) current_records.get(i).getLayoutY();
				int futurex = future_records.get(i).getX();
				int futurey = future_records.get(i).getY();
				System.out.println(currentx+" "+currenty+" "+futurex+" "+futurey);
				if (currentx != futurex || currenty != futurey) {
					transportShape(current_records.get(i), currentx, currenty, futurex, futurey);
				}
			}else {
				gridpane.getChildren().remove(current_records.get(i));
			}

		}
	}
	
	public void transportShape(Shape shape, int current_x ,
			int current_y, int aim_x, int aim_y) {
		
		double duration = ((Math.abs(aim_x-current_x) + Math.abs(aim_y - current_y))/60);
		System.out.println("the duration: "+duration);
		Duration TRANSLATE_DURATION = Duration.seconds(duration);
		TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, shape);
		
	    transition.setToX(aim_x - current_x);
        transition.setToY(aim_y - current_y);
        transition.playFromStart();
	}
	
	public Coordinate extractCoordinate(Record record) {
		Coordinate coordinate = new Coordinate(30+record.posx*60, 30+record.posy*60);
		return coordinate;
	}
	
	public void clearTraffics(Group gridpane) {
		for (int i = 0; i < traffics.size(); i++) {
			gridpane.getChildren().remove(traffics.get(i));
		}
	}
	
	private ArrayList<ArrayList<Record>> getRecords() {
		// TODO Auto-generated method stub
		Testoutput test = new Testoutput();
		return test.fullrecords;
	}
	
	public static void main(String[] args) {
        Application.launch(args);
    }
}
