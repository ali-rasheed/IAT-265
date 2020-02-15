import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TestRotation extends JPanel implements ActionListener {
	private float angle = 0;
	Timer timer;

	public TestRotation() {
		setBackground(new Color(255, 255, 255));
		timer = new Timer(10, this);
		timer.start();
		timer.addActionListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Call JPanel's method to clear the background
		Graphics2D g2 = (Graphics2D) g;
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//g2.translate(35, 35);
		g2.rotate(angle);
		g2.setColor(new Color(255, 128, 0));
		g2.fillRect(35, 35, 50, 50);
		//g2.fillRect(-25, -25, 50, 50);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		angle += 0.01;
		repaint();

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test Rotation");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TestRotation tRot = new TestRotation();
		frame.add(tRot);
		frame.setVisible(true);
	}

}