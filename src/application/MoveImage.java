package application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class MoveImage extends JLayeredPane implements ActionListener{

	/**
	 * Create the panel.
	 */
	private ArrayList<ArrayList<Record>> records = new ArrayList<ArrayList<Record>>();
	private ArrayList<OldCoordinate> oldrecords = new ArrayList<OldCoordinate>();
	private ArrayList<BufferedImage> image = new ArrayList<BufferedImage>();
	private int round = 0;
	
	public MoveImage() {
		records = getRecords();
		for (int i = 0; i < records.get(0).size(); i++) {
			Record current_record = records.get(0).get(i);
			if (current_record.action.equals("appear")) {
				oldrecords.add(new OldCoordinate(current_record.selfname, current_record.posx, current_record.posy));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub	
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
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
