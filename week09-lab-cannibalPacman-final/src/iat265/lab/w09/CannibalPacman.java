package iat265.lab.w09;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

public class CannibalPacman extends Pacman{
	
	private Polygon tooth; 

	public CannibalPacman(float x, float y, float size) {
		super(x, y, size);
	}
	
	@Override
	protected void setShapeAttributes() {
		super.setShapeAttributes();
		
		
		int[] px = {(int)(width*.3f), (int)(width*.4f), (int)(width*.5f)};
		int[] py = {0, -(int)(width*.2f), 0};
		tooth = new Polygon(px, py, px.length);
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		AffineTransform at = g.getTransform();		
		g.translate(position.x, position.y);
		g.rotate(speed.heading());
		g.scale(size, size);
		if (speed.x < 0) g.scale(1, -1);
		
		g.setColor(Color.BLACK);
		//draw below tooth
		AffineTransform at2 = g.getTransform();
		g.rotate(Math.PI/6);
		g.scale(1, .5f);
		g.fillPolygon(tooth);
		g.setTransform(at2);
		
		//draw upper tooth
		g.rotate(-Math.PI/6);
		g.scale(1, -1);
		g.fillPolygon(tooth);
		
		g.setTransform(at);
		
		drawInfo(g);
	}
	
	@Override
	protected boolean eatable(SimulationObject food) {
		return (food instanceof Food) || (food instanceof Animal && !(food instanceof CannibalPacman) && !(food instanceof RoyalPacwoman) && food.getSize() < size);
	}

}
