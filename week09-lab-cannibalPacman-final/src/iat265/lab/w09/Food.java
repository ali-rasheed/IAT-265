package iat265.lab.w09;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Food extends SimulationObject {
	
	private Ellipse2D.Double foodShape;		//geometric shape
	private Color foodColor;					//shape color
	
	public Food(float x, float y, float size) {
		super(x, y, 10, 10, size);
		this.foodColor = Color.YELLOW;
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform at = g.getTransform();
		
		g.translate(position.x, position.y);
		g.scale(size, size);
		
		//draw food
		g.setColor(foodColor);
		g.fill(foodShape);
		
		g.setTransform(at);
	}

	@Override
	protected void setShapeAttributes() {
		this.foodShape = new Ellipse2D.Double(-width/2, -height/2, width, height);	
	}

	@Override
	protected void setBoundingBox() {
		boundingBox = new Area(foodShape);	
	}

	@Override
	public AffineTransform getAffineTransform() {
		AffineTransform at = new AffineTransform();		
		at.translate(position.x, position.y);
		at.scale(size, size);
		return at;
	}

	@Override
	public void update(ArrayList<SimulationObject> objList) {
		// nothing, food don't need to be updated
	}

}
