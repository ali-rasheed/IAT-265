package iat265.lab.w09;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Pacman extends Animal {
	
	private Color color;						//featured color
	//-----------------------------------------------------------------
	private Arc2D.Double head;				//the original pacman body
	private Ellipse2D.Double eye;			//the eye
	
	
	public Pacman(float x, float y, float size) {
		super(x, y, 10, 10, size);
		this.color = Util.randomColor();
	}

	@Override
	protected void setShapeAttributes() {
		head = new Arc2D.Double(-width/2, -height/2, width, height, 30, 300, Arc2D.PIE);
		eye = new Ellipse2D.Double(0, -height/4, width/10, width/10);
	}

	@Override
	public void draw(Graphics2D g) {	
		//transformation
		AffineTransform at = g.getTransform();		
		g.translate(position.x, position.y);
		g.rotate(speed.heading());
		g.scale(size, size);
		if (speed.x < 0) g.scale(1, -1);
		
		//body
		g.setColor(color);
		g.fill(head);
		
		//eye
		g.setColor(Color.black);
		if (energy < FULL_ENERGY*.3f) g.setColor(Util.randomColor());
		g.fill(eye);
		
		g.setTransform(at);
		
		drawInfo(g);
	}

	@Override
	protected void setBoundingBox() {
		boundingBox = new Area(head);
	}

	@Override
	public AffineTransform getAffineTransform() {
		AffineTransform at = new AffineTransform();		
		at.translate(position.x, position.y);
		at.rotate(speed.heading());
		at.scale(size, size);
		return at;
	}

	@Override
	protected boolean eatable(SimulationObject food) {
		return (food instanceof Food);
	}
	
}
