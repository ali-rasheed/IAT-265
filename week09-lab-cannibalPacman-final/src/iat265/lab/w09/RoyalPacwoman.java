package iat265.lab.w09;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class RoyalPacwoman extends Pacwoman{
	
	private Image badge; 

	public RoyalPacwoman(float x, float y, float size) {
		super(x, y, size);
	}
	
	@Override
	protected void setShapeAttributes() {
		super.setShapeAttributes();
		
		try {
		    badge = ImageIO.read(new File("asset/starbadge.png"));
		} catch (IOException e) {
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		AffineTransform at = g.getTransform();		
		g.translate(position.x, position.y);
		g.rotate(speed.heading());
		g.scale(size, size);
		if (speed.x < 0) g.scale(1, -1);
		
		//draw badge
		g.drawImage(badge, (int)(width/20), 0, (int)(height/3), (int)(height/3), null);
		
		g.setTransform(at);
		
		drawInfo(g);
	}

}
