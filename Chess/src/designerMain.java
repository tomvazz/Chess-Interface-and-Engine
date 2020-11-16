import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class designerMain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					designerMain window = new designerMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public designerMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(320, 30, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		Gameplay g = new Gameplay();
		g.appComponants(frame);
		
		frame.getContentPane().setLayout(null);
		
		JLabel rand = new JLabel("g");
		rand.setForeground(new Color(221, 160, 221));
		rand.setFont(new Font("Webdings", Font.PLAIN, 75));
		rand.setBounds(0, 0, 75, 75);
		frame.getContentPane().add(rand);
		JLabel rand1 = new JLabel("g");
		rand1.setForeground(new Color(255, 182, 193));
		rand1.setFont(new Font("Webdings", Font.PLAIN, 75));
		rand1.setBounds(0, 80, 75, 75);
		frame.getContentPane().add(rand1);
		
		
	}
}
