package application;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;

public class Swing_Animation {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Swing_Animation window = new Swing_Animation();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public Swing_Animation() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		Background_Pane bp = new Background_Pane();
		MoveImage mi = new MoveImage();
		frame = new JFrame();
		frame.setBounds(100, 100, 680, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(bp);
//		frame.add(mi);
	}

}
