package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	
	private ArrayList<ArrayList<Record>> records = new ArrayList<ArrayList<Record>>();
	private ArrayList<Shape> traffics = new ArrayList<Shape>();
	private ArrayList<ImageView> crashes = new ArrayList<ImageView>();
	private HashMap<String, Shape> current_records = new HashMap<String, Shape>();
	private HashMap<String, Coordinate> future_records = new HashMap<String, Coordinate>();
	private int round = -1;
	
	
	
	public ButtonClickSimulation(ArrayList<Traffic> traffic_collection) {
		super();
		this.records = getRecords(traffic_collection);
	}

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
				try {
					ShowEachRoundAnimation(root);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	    	
	    });
	    
	    gameGrid.add(next, 0, 0);
	    root.getChildren().add(gameGrid);
	
	    
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
		System.out.println("record array: "+record_array.size());
		clearTraffics(gridpane);
		traffics.clear();
		
		Shape shape;
		for (int i = 0; i < record_array.size(); i++) {
			Record record = record_array.get(i);
			System.out.println(record.selftype + " " + record.selfname + " " + record.action +" "+ record.posx + "," + record.posy);
			if (record.action.equals("appear") || record.action.equals("move") || 
					record.action.equals("stop")) {
				if (record.selftype.equals("Car")) {
					shape = new Circle(30);
					gridpane.add(shape, record.posx, record.posy);
					System.out.println(shape.getLayoutX());
					traffics.add(shape);
				}else if(record.selftype.equals("Walker")) {
					shape = new Rectangle(60, 60);
					gridpane.add(shape, record.posx, record.posy);
					traffics.add(shape);
				}
			}
		}
	}
	
	public void ShowEachRoundAnimation(Group gridpane) throws FileNotFoundException {
		System.out.println(round);
		ArrayList<Record> round0 = records.get(round);
		ArrayList<Record> round1 = records.get(round+1);
		Shape shape;
		clearTraffics(gridpane);
		clearCrashes(gridpane);
		traffics.clear();
		current_records.clear();
		future_records.clear();
		
		for (int i = 0; i < round0.size(); i++) {
			Record record = round0.get(i);
			System.out.println(record);
			int x = record.posx*60+30;
			int y = record.posy*60+30;
//			System.out.println(x+" "+y);
			if (record.action.equals("appear") || record.action.equals("move") || 
					record.action.equals("stop")) {
				if (record.selftype.equals("Car")) {
					shape = new Rectangle(60, 60);
					shape.setLayoutX(x-30);
					shape.setLayoutY(y-30);
					gridpane.getChildren().add(shape);
					traffics.add(shape);
					String traffic_id = record.selftype+record.selfname;
					current_records.put(traffic_id, shape);
				}else if(record.selftype.equals("Walker")) {
					shape = new Circle(30);
					shape.setLayoutX(x);
					shape.setLayoutY(y);
					traffics.add(shape);
					gridpane.getChildren().add(shape);
					String traffic_id = record.selftype+record.selfname;
					current_records.put(traffic_id, shape);
				}
			}else if(record.action.equals("crash")) {
				Image image = new Image(new FileInputStream("src/application/img/crash.png"));
				ImageView imageView = new ImageView(image); 
				imageView.setLayoutX(x-30);
				imageView.setLayoutY(y-30);
				imageView.setFitHeight(60); 
			    imageView.setFitWidth(60); 
			    gridpane.getChildren().add(imageView);
			    crashes.add(imageView);
			}
		}
		
		
//		for (int i = 0; i < traffics.size(); i++) {
//			gridpane.getChildren().add(traffics.get(i));
//		}
		
		for (int i = 0; i < round1.size(); i++) {
			Record record = round1.get(i);
			if (record.action.equals("appear") || record.action.equals("move") ||
					record.action.equals("stop")) {
				Coordinate coordinate = extractCoordinate(record, record.selftype);
				String traffic_id = record.selftype + record.selfname;
				future_records.put(traffic_id, coordinate);
			}
		}
		
		for (String i : current_records.keySet()) {
			if (future_records.get(i) != null) {
				System.out.println(i);
				int currentx = (int) current_records.get(i).getLayoutX();
				int currenty = (int) current_records.get(i).getLayoutY();
				int futurex = future_records.get(i).getX();
				int futurey = future_records.get(i).getY();
				System.out.println(i+" "+currentx+" "+currenty+" "+futurex+" "+futurey);
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
		Duration TRANSLATE_DURATION = Duration.seconds(duration);
		TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, shape);
		
	    transition.setToX(aim_x - current_x);
        transition.setToY(aim_y - current_y);
        transition.playFromStart();
	}
	
	public Coordinate extractCoordinate(Record record, String type) {
		if (type.equals("Car")) {
			Coordinate coordinate = new Coordinate(record.posx*60, record.posy*60);
			return coordinate;
		}else if(type.equals("Walker")) {
			Coordinate coordinate = new Coordinate(30+record.posx*60, 30+record.posy*60);
			return coordinate;
		}
		return null;
	}
	
	public void clearTraffics(Group gridpane) {
		for (int i = 0; i < traffics.size(); i++) {
			gridpane.getChildren().remove(traffics.get(i));
		}
	}
	
	public void clearTraffics(GridPane gridpane) {
		for (int i = 0; i < traffics.size(); i++) {
			gridpane.getChildren().remove(traffics.get(i));
		}
	}
	
	public void clearCrashes(Group root) {
		for (int i = 0; i < crashes.size(); i++) {
			root.getChildren().remove(crashes.get(i));
		}
	}
	
	private ArrayList<ArrayList<Record>> getRecords(ArrayList<Traffic> traffic_collection) {
		// TODO Auto-generated method stub
		System.out.println((traffic_collection==null)+" "+"Simulation page");
		Simulation openn=new Simulation(traffic_collection);
		ArrayList<ArrayList<Record>> result = new ArrayList<ArrayList<Record>>();
		try
		{
			openn.start_simulate();
			result = openn.getFullRecord();
	        for(int i=0;i<result.size();i++){
	            System.out.println("round "+i+" :");
	            for(Record r:result.get(i)){
	                System.out.println("     "+r);
	            }
	        }
		}
		catch(Exception e1) {
			e1.printStackTrace();
		}
		return result;
	}
	
	private ArrayList<ArrayList<Record>> getRecords(){
		Testoutput t = new Testoutput();
		return t.fullrecords;
	}
	
	public static void main(String[] args) {
        Application.launch(args);
    }
}
