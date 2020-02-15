import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import processing.core.PVector;

public class Pacman {
	
	private PVector pos, speed;				//position and speed
	private float maxSpeed;					//speed limit
	private Dimension dimension;				//dimension, includes width and height
	private float scale;						//size
	private Color color;						//main color
	private Arc2D.Double head;				//the original pacman body
	private Ellipse2D.Double eye;			//the eye
//	private Polygon tail;					//tail
	private Area bBox;						//bounding box
	
	
	public Pacman(float x, float y, float size) {
		
		// given attributes
		this.pos = new PVector(x, y);
		this.scale = size;
		
		// attributes set by default
		while (maxSpeed == 0) this.maxSpeed = Util.random(3,5);
		this.speed = Util.randomPVector(maxSpeed);	//random direction of the speed 5 pixels/frame
		this.dimension = new Dimension(200,100);		//default size = 200 x 100
		this.color = Util.randomColor();				//random color
		setShapeAttributes();
	}

	private void setShapeAttributes() {
		head = new Arc2D.Double(0, -dimension.height/2, dimension.width/2, dimension.height, 30, 300, Arc2D.PIE);
		eye = new Ellipse2D.Double(dimension.width/4, -dimension.height/4, dimension.width/20, dimension.width/20);
//		
//		int[] px = {dimension.width/4, -dimension.width/2, -dimension.width/5, -dimension.width/2};
//		int[] py = {0, -dimension.height/2, 0, dimension.height/2};
//		tail = new Polygon(px, py, px.length);
		
		bBox = new Area(head);
//		bBox.add(new Area(tail));
	}

	public void draw(Graphics2D g) {
		
		//transformation
		AffineTransform at = g.getTransform();		
		g.translate(pos.x, pos.y);
		g.rotate(speed.heading());
		g.scale(scale, scale);
		if (speed.x < 0) g.scale(1, -1);
		
//		//tail
//		g.setColor(color.darker());
//		g.fill(tail);
		
		//body
		g.setColor(color);
		g.fill(head);
		
		//eye
		g.setColor(Color.black);
		g.fill(eye);
		
		g.setTransform(at);
		
		//draw bounding box
		//g.setColor(Color.red);
		//g.drawRect((int)(pos.x - dimension.width*scale/2), (int)(pos.y - dimension.height*scale/2), (int)(dimension.width*scale), (int)(dimension.height*scale));
		
		//another bounding box
		//g.setColor(Color.PINK);
		//g.draw(getBoundary().getBounds2D());
	}
	
	public void move() {
		pos.add(speed);
	}
	
	public void checkCollision(Dimension panelSize) {
		// old code
		//if ((pos.x < dimension.width/2 * scale) || (pos.x > panelSize.width - dimension.width/2 * scale)) speed.x *= -1;
		//if ((pos.y < dimension.height/2 * scale) || (pos.y > panelSize.height - dimension.height/2 * scale)) speed.y *= -1;
		
		Rectangle2D.Double top = new Rectangle2D.Double(0, -10, panelSize.width, 10);
		Rectangle2D.Double bottom = new Rectangle2D.Double(0, panelSize.height, panelSize.width, 10);
		Rectangle2D.Double left = new Rectangle2D.Double(-10, 0, 10, panelSize.height);
		Rectangle2D.Double right = new Rectangle2D.Double(panelSize.width, 0, 10, panelSize.height);
		
		if (getBoundary().intersects(left) && speed.x < 0) speed.x *= -1;
		if (getBoundary().intersects(right) && speed.x > 0) speed.x *= -1;
		if (getBoundary().intersects(top) && speed.y < 0) speed.y *= -1;
		if (getBoundary().intersects(bottom) && speed.y > 0) speed.y *= -1;
	}

	public Shape getBoundary() {
		AffineTransform at = new AffineTransform();		
		at.translate(pos.x, pos.y);
		at.rotate(speed.heading());
		at.scale(scale, scale);
		return at.createTransformedShape(bBox);
	}

	public void enlarge() {
		scale *= 1.1;
	}
	
	public void shrink() {
		scale *= .9;
	}
	
	public boolean checkMouseHit(MouseEvent e) {
//		return	(Math.abs(e.getX() - pos.x) < dimension.width/2) && 
//				(Math.abs(e.getY() - pos.y) < dimension.height/2);
		return getBoundary().contains(e.getX(), e.getY());
	}

	public PVector getPos() {
		return pos;
	}

	public void attractedBy(Food target) {
		PVector path = PVector.sub(target.getPos(), pos);
		speed.add(path.normalize().mult(maxSpeed*.2f)).limit(maxSpeed);
	}

	public boolean collides(Food food) {
		return (getBoundary().intersects(food.getBoundary().getBounds2D()) &&
				food.getBoundary().intersects(getBoundary().getBounds2D()) );
	}

	public boolean collides(Pacwoman p) {
		return (getBoundary().intersects(p.getBoundary().getBounds2D()) &&
				p.getBoundary().intersects(getBoundary().getBounds2D()) );
	}

	public void distractedBy(Pacman p) {
		PVector path = PVector.sub(pos, p.pos);
		speed.add(path.normalize().mult(maxSpeed*2f)).limit(maxSpeed);
	}
	public boolean collides(Pacman p) {
		return (getBoundary().intersects(p.getBoundary().getBounds2D()) &&
				p.getBoundary().intersects(getBoundary().getBounds2D()) );
	}

	public void distractedBy(Pacwoman p) {
		PVector path = PVector.sub(pos, p.getPos());
		speed.add(path.normalize().mult(maxSpeed*2f)).limit(maxSpeed);
	}

	public float getSize() {
		return scale;
	}
}

