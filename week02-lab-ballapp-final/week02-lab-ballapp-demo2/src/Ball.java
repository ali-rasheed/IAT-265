import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;

public class Ball {

    private int x, y, size;
    private int speedX, speedY;
    private int red = (int) (Math.random() * 255);
    private int green = (int) (Math.random() * 255);
    private int blue = (int) (Math.random() * 255);

    public Ball(int x, int y, int size) {
	this.x = x;
	this.y = y;
	this.size = size;
    }

    public Ball(int x, int y, int size, int speedX, int speedY) {
	this.x = x;
	this.y = y;
	this.size = size;
	this.speedX = speedX;
	this.speedY = speedY;
    }

    public void draw(Graphics g) {
	Color c = new Color(red, green, blue);
	g.setColor(c);
	g.fillOval(x - size / 2, y - size / 2, size, size);
    }

    public void move() {
	x += speedX;
	y += speedY;
    }

    public void checkCollision(Dimension panelSize) {
	if ((x < size / 2) || (x > panelSize.width - size / 2)) {
	    speedX *= -1;
	    red = (int) (Math.random() * 255);
	    green = (int) (Math.random() * 255);
	    blue = (int) (Math.random() * 255);
	}
	if ((y < size / 2) || (y > panelSize.height - size / 2)) {
	    speedY *= -1;
	    red = (int) (Math.random() * 255);
	    green = (int) (Math.random() * 255);
	    blue = (int) (Math.random() * 255);
	}
    }

}
