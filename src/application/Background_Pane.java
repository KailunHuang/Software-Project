package application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import application.MoveImage.OldCoordinate;

public class Background_Pane extends JLayeredPane {

	/**
	 * Create the panel.
	 */
	private ArrayList<ArrayList<Record>> records = new ArrayList<ArrayList<Record>>();
	private ArrayList<OldCoordinate> oldrecords = new ArrayList<OldCoordinate>();
	private ArrayList<BufferedImage> image = new ArrayList<BufferedImage>();
	private int round = 0;
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
		for (int i = 0; i < records.get(0).size(); i++) {
			Record current_record = records.get(0).get(i);
			if (current_record.action.equals("appear")) {
				oldrecords.add(new OldCoordinate(current_record.selfname, current_record.posx, current_record.posy));
			}
		}
		
//		repaint();
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
        if (round==0) {
			ArrayList<Record> init_records = records.get(0);
			for (int i = 0; i < init_records.size(); i++) {
				Record record = init_records.get(i);
				if(record.selftype.equals("car")) {
					g.setColor(Color.blue);
					g.drawOval(record.posx*60, record.posy*60, 60, 60);
				}
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
		
		class OldCoordinate{
			public String name;
			public int x;
			public int y;
			
			public OldCoordinate(String name, int x, int y) {
				this.name = name;
				this.x = x;
				this.y = y;
			}
			
		}
	 
}
