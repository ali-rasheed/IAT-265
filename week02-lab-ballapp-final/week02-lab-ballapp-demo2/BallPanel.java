import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class BallPanel extends JPanel implements ActionListener {
	
	private Ball ball;
	private Timer t; 
	
	public BallPanel(Dimension initialSize) {
		super();
		int randomSpeed = (int) (Math.random()*50 - 25);
		int randomSpeed2 = (int) (Math.random()*50 - 25);
		System.out.println(randomSpeed);
		ball = new Ball(initialSize.width/2, initialSize.height/2, Math.min(initialSize.width,initialSize.height)/10, randomSpeed,randomSpeed2 );
		t = new Timer(33, this);
		t.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.black);
		ball.draw(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ball.move();
		ball.checkCollision(getSize());
		repaint();
	}
	
}
