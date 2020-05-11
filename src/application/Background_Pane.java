package application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import application.MoveImage.OldCoordinate;

public class Background_Pane extends JLayeredPane {

	/**
	 * Create the panel.
	 */
	private ArrayList<ArrayList<Record>> records = new ArrayList<ArrayList<Record>>();
	private HashMap<String, Coordinate> current_coordinate = new HashMap<String, Coordinate>();
	private HashMap<String, Coordinate> future_coordinate = new HashMap<String, Coordinate>();
	private ArrayList<BufferedImage> image = new ArrayList<BufferedImage>();
	private int round = 1;
	private ArrayList<BufferedImage> row_image;
	private ArrayList<BufferedImage> column_image;
	private int column_number;
	private int row_number;
	
	public Background_Pane() throws IOException {
		row_image = new ArrayList<BufferedImage>();
		column_image = new ArrayList<BufferedImage>();
		column_number = 11;
		row_number = 8;
		for (int i = 0; i < row_number; i++) {
			BufferedImage image = ImageIO.read(new File("src/application/img/road.png"));
			row_image.add(image);
		}
		
		for (int i = 0; i < column_number; i++) {
			BufferedImage image = ImageIO.read(new File("src/application/img/road.png"));
			column_image.add(image);
		}
		
		records = getRecords();
		
//		Read initial traffics 
		ArrayList<Record> Round1 = records.get(0);
		ArrayList<Record> Round2 = records.get(0);
		for (int i =0; i < Round1.size(); i++) {
			Record current_r = Round1.get(i);
			if (current_r.action.equals("appear")) {
				current_coordinate.put(current_r.selfname, new Coordinate(current_r.posx, current_r.posy));
			}
		}
		
		for (int i = 0; i < Round2.size(); i++) {
			Record future_r = Round2.get(0);
			if (future_r.action.equals("appear") || future_r.action.equals("move")) {
				future_coordinate.put(future_r.selfname, new Coordinate(future_r.posx, future_r.posy));
			}
		}
	}
	
	 @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);        
        paintRow(g, 3);
        paintRow(g, 4);
        paintColumn(g, 2);
        paintColumn(g, 3);
        paintColumn(g, 7);
        paintColumn(g, 8);
        
//      at the beginning of the simulation, just draw the round 1 traffics
        System.out.println("size: "+current_coordinate.size());
        if (round==1) {
        	for (String i : current_coordinate.keySet()) {
        		System.out.println(current_coordinate.get(i));
        		g.setColor(Color.blue);
        		g.fillOval((current_coordinate.get(i).x)*60, (current_coordinate.get(i).y)*60, 60, 60);
        	}
        }
        

    }
	
	 protected void paintRow(Graphics g, int row) {
		 for (int i=0; i < column_image.size(); i++) {
			 System.out.print(i+" ");
			 g.drawImage(column_image.get(i), i*60, row*60, 60, 60, this);
		 }
	 }
	 
	 protected void paintColumn(Graphics g, int column) {
		 for (int i=0; i < row_image.size(); i++) {
			 g.drawImage(row_image.get(i), column*60, i*60, 60, 60, this);
		 }
	 }
	 
	 public ArrayList<ArrayList<Record>> getRecords() {
			Testoutput t = new Testoutput();
			return t.fullrecords;		
	}
		
	class Coordinate{
		public int x;
		public int y;
		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
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
	        return Double.compare(x, c.x) == 0
	                && Double.compare(y, c.y) == 0; 
	    } 
		
	}
	 
}
