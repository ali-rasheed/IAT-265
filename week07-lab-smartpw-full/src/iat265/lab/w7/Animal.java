package iat265.lab.w7;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import processing.core.PVector;

public class Animal extends SimulatedObject{
	protected PVector speed;
	protected float maxSpeed;					//speed limit
	protected Dimension dimension;				//dimension, includes width and height
	protected float scale;						//size
	protected Color color;						//main color
	protected Arc2D.Double head;				//the original pacman body
	protected Ellipse2D.Double eye;			//the eye
	protected Area bBox;	
	
	public Animal(float x, float y, float size) {
		
		// given attributes
		this.pos = new PVector(x, y);
		this.scale = size;
		
		// attributes set by default
		this.maxSpeed = 3;
		this.speed = new PVector(maxSpeed,0);		//random direction of the speed 5 pixels/frame
		this.dimension = new Dimension(200,100);		//default size = 200 x 100
		this.color = Util.randomColor();				//random color
		setShapeAttributes();
	}

	private void setShapeAttributes() {

		// TODO Auto-generated method stub
		
	}
}
