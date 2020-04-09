package roadSimulation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class welcomePage {
	private static JFrame welframe = new JFrame("welcome");
	private static Container c = welframe.getContentPane();
	private static JLabel welLable = new JLabel("username");
	private static JButton newSimulation = new JButton("New Simulation");
	private static JButton load = new JButton("Load Simulation File");
	public welcomePage() {
	
	welframe.setBounds(600, 200, 300, 220);
	     
	c.setLayout(new BorderLayout());
    welframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      
	        initial();
	       
    welframe.setVisible(true);
	     }
	public void initial() {
	    JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
	         JPanel button = new JPanel();
	        button.setLayout(new FlowLayout());
	      button.add(newSimulation);
	        button.add(load);
	         c.add(button);
	   }
	     public static void main(String[] a) {
	        welcomePage w=new welcomePage();
	        ActionListener bb=new ActionListener() {
	        	public void actionPerformed(ActionEvent arg0) {
	        		configure mm=new configure();
	        		w.welframe.dispose();
	        	}
	        };
	        ActionListener cc=new ActionListener() {
	        	public void actionPerformed(ActionEvent b) {
	        		overview nn=new overview();
	        		w.welframe.dispose();
	        	}
	        };
	        newSimulation.addActionListener(bb);
	        load.addActionListener(cc);
	     } 
}
