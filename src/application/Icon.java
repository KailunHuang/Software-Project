package application;

import javafx.scene.shape.Shape;

public class Icon{
	
	private Shape shape;
	private String name;

	
	public Icon(Shape shape, String name) {
		this.name = name;
		this.shape = shape;
	}


	public Shape getShape() {
		return shape;
	}


	public void setShape(Shape shape) {
		this.shape = shape;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
