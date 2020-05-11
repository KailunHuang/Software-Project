package application;

import com.sun.javafx.geom.Shape;

public class IconLocation {
	
	private Shape shape;
	private String name;

	
	public IconLocation(Shape shape, String name) {
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
