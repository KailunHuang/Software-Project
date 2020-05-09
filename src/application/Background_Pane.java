package application;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Background_Pane extends JPanel {

	/**
	 * Create the panel.
	 */
	
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
	 
}
