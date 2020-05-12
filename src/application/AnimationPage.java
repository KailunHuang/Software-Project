package application;

import java.io.FileInputStream;

import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationPage extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Image road_image = new Image("application/img/road.png");
		ArrayList<ImageView> row= new ArrayList<ImageView>();
		ArrayList<ImageView> row2= new ArrayList<ImageView>();
		for (int i = 0; i < 13; i++) {
			ImageView imageView = new ImageView(road_image); 
			ImageView imageView2 = new ImageView(road_image);
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
			for (int i = 0; i < 10; i++) {
				ImageView imageView = new ImageView(road_image);
				imageView.setFitWidth(60);
				imageView.setFitHeight(60);
				views.add(imageView);
			}
			column.add(views);
		} 
		
		
		
		int[] column_nums = {3,4,8,9};
		
		Image car = new Image(new FileInputStream("C:\\Graduate Education\\Second Year\\Software program\\Software-Project\\src\\application\\img\\car.png"));  
		ImageView carview = new ImageView(car);
		carview.setFitWidth(60);
		carview.setFitHeight(60);
		TranslateTransition translation = new TranslateTransition();
		translation.setDuration(Duration.millis(1000));
		translation.setNode(carview);
		translation.setByX(180);
		translation.setAutoReverse(false);
		translation.play();
		
		TranslateTransition ntranslation = new TranslateTransition();
		ntranslation.setDuration(Duration.millis(1000));
		ntranslation.setNode(carview);
		ntranslation.setByX(120);
		ntranslation.setAutoReverse(false);
		ntranslation.play();
		
		GridPane gridPane = new GridPane();
		
		for (int i=0; i < row.size(); i++) {
			gridPane.add(row.get(i), i, 4);
			gridPane.add(row2.get(i), i, 5);
		}
		
		for (int j=0; j < column.size(); j++) {
			int n = column_nums[j];
			for (int i=0; i < column.get(j).size(); i++) {
				gridPane.add(column.get(j).get(i), n, i);
			}
		}

		gridPane.add(carview, 0, 3);
		System.out.println(carview.getX());
		System.out.println(carview.getY());
		Scene scene = new Scene(gridPane, 780, 560);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public static void main(String[] args) {
        Application.launch(args);
    }
	
}
