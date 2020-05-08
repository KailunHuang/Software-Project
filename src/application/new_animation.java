package application;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class new_animation extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Group root = new Group();
		
		int rows = 8;
		int columns = 11;
		
		GridPane gameGrid = new GridPane();

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
	    
	    ImageView cyclist = new ImageView(new Image("application/img/bike.png"));
	    cyclist.setFitHeight(60);
	    cyclist.setFitWidth(60);
	    cyclist.setLayoutX(0);
	    cyclist.setLayoutY(180);
	    Circle circle = new Circle(30, 210, 30);
	    root.getChildren().add(gameGrid);
	    root.getChildren().add(cyclist);
	    root.getChildren().add(circle);
	    Scene scene = new Scene(root, columns*60, rows*60, Color.WHITE);
	    
//	    transportImage(root, cyclist, 0, 180, 120, 180);
	    transportShape(circle, 30, 210, 150, 210);
	    transportShape(circle, 150, 210, 330, 210);
	    
	    
	    
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
	
	public void transportShape(Circle circle, double current_x ,
			double current_y, double aim_x, double aim_y) {
		
		double duration = ((Math.abs(aim_x-current_x) + Math.abs(aim_y - current_y))/60);
		System.out.println("the duration: "+duration);
		Duration TRANSLATE_DURATION = Duration.seconds(duration);
		TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
		
//	    transition.setOnFinished(new EventHandler<ActionEvent>() {
//	      @Override public void handle(ActionEvent t) {
//	        circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
//	        circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
//	        circle.setCenterX(aim_x);
//	        circle.setCenterY(aim_y);
//	        System.out.println(circle.getTranslateX());
//	        System.out.println(circle.getTranslateY());
//	        System.out.println(circle.getCenterX());
//	        System.out.println(circle.getCenterY());
//	        System.out.print("\n");
//	        circle.setTranslateX(0);
//	        circle.setTranslateY(0);
//	        
//	      }
//	    });
		
	    transition.setToX(aim_x - circle.getCenterX());
        transition.setToY(aim_y - circle.getCenterY());
        transition.playFromStart();
        System.out.println(circle.getCenterX());
        System.out.println(circle.getCenterY());
//        circle.setCenterX(aim_x);
//        circle.setCenterY(aim_y);
	}
	
	
	public void transportImage(Group pane, ImageView imageview, double current_x ,
			double current_y, double aim_x, double aim_y) throws InterruptedException {
//		TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
	}
	
	public static void main(String[] args) {
        Application.launch(args);
    }
}
