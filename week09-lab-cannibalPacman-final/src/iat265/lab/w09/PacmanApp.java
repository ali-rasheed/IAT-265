package iat265.lab.w09;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class PacmanApp extends JFrame {

	private static final long serialVersionUID = 6457792220456140992L;

	public PacmanApp(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		ControlPanel cPanel = new ControlPanel();
		PacmanPanel mainPanel = new PacmanPanel(cPanel);
		
		Container contentPane = getContentPane();
        contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BorderLayout());
		
		
		contentPane.add(cPanel, BorderLayout.SOUTH);
		
		
		contentPane.add(mainPanel, BorderLayout.CENTER);
		
		
		
		this.pack();
		//displaying the frame
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new PacmanApp("Pacman Habitat");
	}

}
