import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PVector;

public class Pacwoman {
	
	private PVector pos, speed;				//position and speed
	private float maxSpeed;					//speed limit
	private Dimension dimension;				//dimension, includes width and height
	private float scale;						//size
	private Color color;						//main color
	private Arc2D.Double head;				//the original pacman body
	private Ellipse2D.Double eye;			//the eye
	private Polygon tail;					//tail
	private Area bBox;						//bounding box
	
	
	public Pacwoman(float x, float y, float size) {
		
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
		
		int[] px = {dimension.width/4, -dimension.width/2, -dimension.width/5, -dimension.width/2};
		int[] py = {0, -dimension.height/2, 0, dimension.height/2};
		tail = new Polygon(px, py, px.length);
		
		bBox = new Area(head);
		bBox.add(new Area(tail));
	}

	public void draw(Graphics2D g) {	
		//transformation
		AffineTransform at = g.getTransform();		
		g.translate(pos.x, pos.y);
		g.rotate(speed.heading());
		g.scale(scale, scale);
		if (speed.x < 0) g.scale(1, -1);
		
		//tail
		g.setColor(color.darker());
		g.fill(tail);
		
		//body
		g.setColor(color);
		g.fill(head);
		
		//eye
		g.setColor(Color.black);
		g.fill(eye);
		
		g.setTransform(at);
	}
	
	public void move() {
		speed.normalize().mult(maxSpeed);
		pos.add(speed);
	}
	
	public void checkCollision(Dimension panelSize) {
		float margin = 150;
		Rectangle2D.Double top = new Rectangle2D.Double(-margin, -margin, panelSize.width + margin*2, margin);
		Rectangle2D.Double bottom = new Rectangle2D.Double(-margin, panelSize.height, panelSize.width + margin*2, margin);
		Rectangle2D.Double left = new Rectangle2D.Double(-margin, -margin, margin, panelSize.height + margin*2);
		Rectangle2D.Double right = new Rectangle2D.Double(panelSize.width, -margin, margin, panelSize.height + margin*2);
		
		if (getBoundary().intersects(left) && speed.x < 0) speed.x *= -1;
		else if (getBoundary().intersects(right) && speed.x > 0) speed.x *= -1;
		else if (getBoundary().intersects(top) && speed.y < 0) speed.y *= -1;
		else if (getBoundary().intersects(bottom) && speed.y > 0) speed.y *= -1;
	}

	public Shape getBoundary() {
		AffineTransform at = new AffineTransform();		
		at.translate(pos.x, pos.y);
		at.rotate(speed.heading());
		at.scale(scale, scale);
		return at.createTransformedShape(bBox);
	}
	
	public boolean checkMouseHit(MouseEvent e) {
		return getBoundary().contains(e.getX(), e.getY());
	}

	public void attractedBy(Food target) {
		float coef = .2f;	// coefficient of acceleration relative to maxSpeed
		PVector direction = PVector.sub(target.getPos(), pos).normalize();
		PVector acceleration = PVector.mult(direction, maxSpeed*coef);
		speed.add(acceleration);
	}

	public boolean collides(Food food) {
		return (getBoundary().intersects(food.getBoundary().getBounds2D()) &&
				food.getBoundary().intersects(getBoundary().getBounds2D()) );
	}
	
	public void traceClosestFood(ArrayList<Food> fList) {	
		if (fList.size()>0) {
			
			// set the 1st item as default target
			Food target = fList.get(0);
			float distToTarget = PVector.dist(pos, target.getPos());
			
			// find the closer one
			for (Food f:fList) if (PVector.dist(pos, f.getPos()) < distToTarget) {
				target = f;
				distToTarget = PVector.dist(pos, target.getPos());
			}
			
			// make animal follow this target
			this.attractedBy(target);
		}	
	}

}
