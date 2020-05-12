package application;

import javafx.scene.shape.Circle;

public class Coordinate {
	private int x;
	private int y;
	private Circle circle;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
		this.circle = new Circle(x, y, 30);
	}
	
	@Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Coordinate)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Coordinate c = (Coordinate) o; 
          
        // Compare the data members and return accordingly  
        return Integer.compare(x, c.x) == 0
                && Integer.compare(y, c.y) == 0; 
    } 
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	
	@Override
	public String toString() {
		return ("["+x+","+y+"]");
	}
	
}
