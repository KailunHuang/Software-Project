/***
 * This file is created by Kailin Huang (828808).
 * This class is the animation page, after the user finish the configuration page, the traffic data will send to the
 * simulation part, and the output of simulation will be shown up as animation by this page.
 * In total, we have 4 intersection cross, 8 start point in the map.
 * The car types traffics will shows up in the start point randomly and start to move 1 cell each time until the reach
 * the end, and then it will disappear in the map.
 */
package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ButtonClickSimulation extends Application{
	
	private ArrayList<ArrayList<Record>> records = new ArrayList<ArrayList<Record>>();
	private ArrayList<ImageView> traffics = new ArrayList<ImageView>();
	private ArrayList<ImageView> crashes = new ArrayList<ImageView>();
	private HashMap<String, ImageView> current_records = new HashMap<String, ImageView>();
	private HashMap<String, Coordinate> future_records = new HashMap<String, Coordinate>();
	private Timeline timeline;
	private GridPane gameGrid = new GridPane();
	private Group root = new Group();
	private int round = -1;
	
	
	
	public ButtonClickSimulation(ArrayList<ArrayList<Record>> records) {
		super();
		this.records = records;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		int rows = 13;
		int columns = 11;

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

	    //***********************Do the animation every 1 second*********************
	    timeline = new Timeline();
	    timeline.setCycleCount(Timeline.INDEFINITE);
	    
	    int duration = 1000;
	    KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), new EventHandler<ActionEvent>() {
	    	 
            @Override
            public void handle(ActionEvent event) {
            	round++;
				try {
					if (round < records.size()-1) {
						ShowEachRoundAnimation(root);
					}else if(round == records.size()-1){
						ShowEachRoundPosition(gameGrid);
					}else {
//						Outcome outcome = new Outcome(records);
//						outcome.start(new Stage());
						primaryStage.hide();
						timeline.stop();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	    timeline.getKeyFrames().add(keyFrame);
	    timeline.playFromStart();
	    //*************************************************************************************

	    System.out.println("record size: "+records.size());
	     
//	    gameGrid.add(next, 0, 0);
	    root.getChildren().add(gameGrid);
	
	    
	    Scene scene = new Scene(root, columns*60, rows*60, Color.WHITE);
	    primaryStage.setScene(scene);
        primaryStage.show();
	}

	//************load the background images in the panel*******************************
	public static void loadRoadImage(GridPane gridpane) {
		int columns = 13;
		int rows = 11;
		
		Image image = new Image("application/img/road.png");
		ArrayList <ArrayList<ImageView>> row= new ArrayList<ArrayList<ImageView>>();
		int[] row_nums = {3,4,8,9};
		
		for (int i = 0; i < 4; i++) {
			ArrayList<ImageView> views = new ArrayList<ImageView>();
			for (int j = 0; j < rows; j++) {
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(60);
				imageView.setFitHeight(60);
				views.add(imageView);
			}
			row.add(views);
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
		
		for (int j=0; j < row_nums.length; j++) {
			int n = row_nums[j];
			for (int i=0; i < row.get(j).size(); i++) {
				gridpane.add(row.get(j).get(i), i, n);
			}
		}
		
		for (int j=0; j < column.size(); j++) {
			int n = column_nums[j];
			for (int i=0; i < column.get(j).size(); i++) {
				gridpane.add(column.get(j).get(i), n, i);
			}
		}
	}

	//Every time finish the animation, the position of the traffic images should be relocated.
	public void ShowEachRoundPosition(GridPane gridpane) throws FileNotFoundException {
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
					
					Image image = new Image(new FileInputStream("src/application/img/car.png"));
					ImageView imageView = new ImageView(image);
					imageView.setFitHeight(60); 
				    imageView.setFitWidth(60); 
					gridpane.add(imageView, record.posx, record.posy);
					traffics.add(imageView);
					
				}else if(record.selftype.equals("Walker")) {
					Image image = new Image(new FileInputStream("src/application/img/walker_right.png"));
					ImageView imageView = new ImageView(image);
					imageView.setFitHeight(60); 
				    imageView.setFitWidth(60); 
					gridpane.add(imageView, record.posx, record.posy);
					traffics.add(imageView);
				}
				
			}
		}
	}

	//Do the animation for all the traffics in a round
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
					Image image = new Image(new FileInputStream("src/application/img/car.png"));
					ImageView imageView = new ImageView(image); 
					imageView.setLayoutX(x-30);
					imageView.setLayoutY(y-30);
					imageView.setFitHeight(60); 
				    imageView.setFitWidth(60); 
					gridpane.getChildren().add(imageView);
					traffics.add(imageView);
					String traffic_id = record.selftype+record.selfname;
					current_records.put(traffic_id, imageView);
				}else if(record.selftype.equals("Walker")) {
					Image image = new Image(new FileInputStream("src/application/img/walker_right.png"));
					ImageView imageView = new ImageView(image); 
					imageView.setLayoutX(x-30);
					imageView.setLayoutY(y-30);
					imageView.setFitHeight(60); 
				    imageView.setFitWidth(60); 
					gridpane.getChildren().add(imageView);
					traffics.add(imageView);
					String traffic_id = record.selftype+record.selfname;
					current_records.put(traffic_id, imageView);
				}else if(record.selftype.equals("Truck")) {
					Image image = new Image(new FileInputStream("src/application/img/truck.png"));
					ImageView imageView = new ImageView(image); 
					imageView.setLayoutX(x-30);
					imageView.setLayoutY(y-30);
					imageView.setFitHeight(60); 
				    imageView.setFitWidth(60); 
					gridpane.getChildren().add(imageView);
					traffics.add(imageView);
					String traffic_id = record.selftype+record.selfname;
					current_records.put(traffic_id, imageView);
				}else if(record.selftype.equals("Bus")) {
					Image image = new Image(new FileInputStream("src/application/img/bus.png"));
					ImageView imageView = new ImageView(image); 
					imageView.setLayoutX(x-30);
					imageView.setLayoutY(y-30);
					imageView.setFitHeight(60); 
				    imageView.setFitWidth(60); 
					gridpane.getChildren().add(imageView);
					traffics.add(imageView);
					String traffic_id = record.selftype+record.selfname;
					current_records.put(traffic_id, imageView);
				}else if(record.selftype.contentEquals("Cyclist")) {
					Image image = new Image(new FileInputStream("src/application/img/bike.png"));
					ImageView imageView = new ImageView(image); 
					imageView.setLayoutX(x-30);
					imageView.setLayoutY(y-30);
					imageView.setFitHeight(60); 
				    imageView.setFitWidth(60); 
					gridpane.getChildren().add(imageView);
					traffics.add(imageView);
					String traffic_id = record.selftype+record.selfname;
					current_records.put(traffic_id, imageView);
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
//			System.out.println(i);
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
	
	public void transportShape(ImageView shape, int current_x ,
			int current_y, int aim_x, int aim_y) {
		
		double duration = ((Math.abs(aim_x-current_x) + Math.abs(aim_y - current_y))/60);
		Duration TRANSLATE_DURATION = Duration.seconds(duration);
		TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, shape);
		
	    transition.setToX(aim_x - current_x);
        transition.setToY(aim_y - current_y);
        transition.playFromStart();
	}
	
	public Coordinate extractCoordinate(Record record, String type) {
		if (type.equals("Car") || type.equals("Truck") || type.equals("Bus")) {
			Coordinate coordinate = new Coordinate(record.posx*60, record.posy*60);
			return coordinate;
		}else if(type.equals("Walker")) {
			Coordinate coordinate = new Coordinate(record.posx*60, record.posy*60);
			return coordinate;
		}else if(type.equals("Cyclist")) {
			Coordinate coordinate = new Coordinate(record.posx*60, record.posy*60);
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

	
	public static void main(String[] args) {
        Application.launch(args);
    }

}
