/* A Ladybug class that draws and moves a ladybug
 * A demo program created for IAT-265 Spring 15
 * Author: Eric Yang
 * Date of creation: Jan 17 2015
 * Date of Modification: Jan 19 2015
 * All rights reserved
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;

public class Ladybug {
	// Properties of Ladybug object
	private int bugX;
	private int bugY;
	private int bodyW;
	private int bodyH;
	private int speedX;
	private double scale;

	// Constructor
	public Ladybug(int x, int y, int w, int h, int sx, double sc) {
		bugX = x;
		bugY = y;
		bodyW = w;
		bodyH = h;
		speedX = sx;
		scale = sc;
	}

	public void drawBug(Graphics2D g2) {

		g2.translate(bugX, bugY);
		g2.scale(scale, scale);
		// make the bug rotate
		if (speedX < 0) {
			//g2.rotate(Math.PI);
			g2.scale(-1, 1);
		}
		
		//draw body as an oval in orange
		g2.setColor(new Color(160, 0, 0));
		
		//make the new (0, 0) be the center of the bug's body
		g2.drawOval(-bodyW/2, -bodyH/2, bodyW, bodyH);
		g2.fillOval(-bodyW/2, -bodyH/2, bodyW, bodyH);

		// draw body dots and line
		g2.setColor(Color.BLACK);
//		g2.fillOval(10, -12, 7, 7);
//		g2.fillOval(-10, -12, 7, 7);

		g2.fillOval(10, 7, 7, 7);
		g2.fillOval(-10, 7, 7, 7);
		g2.drawLine(-bodyW / 2, 0, bodyW / 2, 0);
		
		// draw antenna as two arcs
//		g2.drawArc(11, -20, bodyW / 2, bodyH / 2, -90, 90);
		g2.drawArc(11, 0, bodyW / 2, bodyH / 2, 0, 90);

		// draw head as a half pie
		g2.fillArc(bodyW/2-6, -6, 12, 12, -90, 180);
	
	}
	
	public void move(){
		bugX = bugX + speedX;
		
		if (bugX + (bodyW / 2 + bodyW / 4)*scale > (BugPanel.GARDEN_X + BugPanel.GARDEN_W)
				|| bugX - (bodyW / 2 + bodyW / 4)*scale < BugPanel.GARDEN_X) {
			speedX = -speedX;
		}
	}
	
}
