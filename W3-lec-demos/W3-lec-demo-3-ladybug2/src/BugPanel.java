import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class BugPanel extends JPanel implements ActionListener {
	private Ladybug bug;
	private Timer timer;
	public final static int GARDEN_X = 50;
	public final static int GARDEN_Y = 50;	
	public final static int GARDEN_W = 500;
	public final static int GARDEN_H = 300;
	
	public BugPanel (){
		setPreferredSize(new Dimension(600, 450));
		timer = new Timer(30, this);
		timer.start();
		bug = new Ladybug(GARDEN_X + 50, GARDEN_Y + GARDEN_H/2, 45, 40, 2);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	//Call JPanel's method to clear the background	
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(0, 200, 0));
		g2.fillRect(GARDEN_X, GARDEN_Y, GARDEN_W, GARDEN_H);
		bug.drawBug(g2);

	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		bug.move();
		repaint();
	}
	
	   public static void main(String[] args) {
	        JFrame frame = new JFrame("Ladybug in Garden");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        BugPanel bugPane = new BugPanel();
	        frame.add(bugPane);
	        frame.pack();
	        frame.setVisible(true);
	    }
}
