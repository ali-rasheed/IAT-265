package iat265.lab.w09;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import processing.core.PVector;

public abstract class Animal extends SimulationObject {
	
	protected PVector speed;					//position and speed
	protected float speedMagnitude;				//speed limit
	protected float energy;					//energy
	protected float FULL_ENERGY = 1000;
	protected float MAX_SPEED = 5;
	protected int age;

	public Animal(float x, float y, float w, float h, float size) {
		super(x, y, w, h, size);
		updateSpeed();
		speed = Util.randomPVector(speedMagnitude);
		energy = FULL_ENERGY;
		age = 0;
	}
	
	protected void updateSpeed() {
		//decrease speed base on size
		float ratio = 1;
		
		if (size < 5f) ratio = 0.1f + 0.18f*size;
		else if (size < 10) ratio = 1f - 0.18f*(size-5f);
		else ratio = .1f;
		
		if (energy < FULL_ENERGY*.3f) ratio *= (3f - 2f*(energy/(FULL_ENERGY*.3f)));
		
		speedMagnitude = ratio * MAX_SPEED;
	}
	
	protected void move() {		
		//lose energy
		energy-= FULL_ENERGY*size/(30*60); //30fps x 60sec living duration
			
		//apply speed to position
		updateSpeed();
		speed.normalize().mult(speedMagnitude);
		position.add(speed);
	}

	protected void attractedBy(SimulationObject target) {
		float coef = .2f;	// coefficient of acceleration relative to maxSpeed
		PVector direction = PVector.sub(target.getPos(), position).normalize();
		PVector acceleration = PVector.mult(direction, speedMagnitude*coef);
		speed.add(acceleration);
	}
	
	protected void pushedAwayFrom(SimulationObject obstacle) {
		float coef = 5f;	// coefficient of acceleration relative to maxSpeed
		PVector direction = PVector.sub(position, obstacle.getPos()).normalize();
		PVector acceleration = PVector.mult(direction, speedMagnitude*coef);
		speed.add(acceleration);
	}
	
	@Override
	public void update(ArrayList<SimulationObject> objList) {
		if (energy < 0) {
			objList.remove(this);
			return;
		}
		age += 33;
		ArrayList<SimulationObject> fList = getTargetList(objList);
		traceBestFood(fList);
		move();
		for (int i = 0; i < fList.size(); i++) 
			if (collides(fList.get(i))) {
				float foodSize = fList.get(i).getSize();
				energy += foodSize*100;
				PacmanPanel.setStatus(String.format("%s gains %.2f units of energy => %.2f%n", animalType(), foodSize*100, energy));
				if (energy > FULL_ENERGY) {
					float residualEnergy = energy - FULL_ENERGY;
					energy = FULL_ENERGY;
					size += residualEnergy*0.0001*size;
					PacmanPanel.setStatus(String.format("%s grows by %.1f%% => %.2f%n", animalType(), residualEnergy*.01f, size));
				} 	
				objList.remove(fList.get(i));
			}
		
		//non-eat-able object
		for (SimulationObject obj:objList) if ((obj instanceof Animal) && obj.getSize() > this.getSize() && this.collides(obj))
			this.pushedAwayFrom(obj);;
	}
	
	private String animalType() {
		String type = "unknown animal";
		if (this instanceof RoyalPacwoman) type = "Royal Pacwoman";
		else if (this instanceof Pacwoman) type = "Pacwoman";
		else if (this instanceof CannibalPacman) type = "Cannibal Pacman";
		else if (this instanceof Pacman) type = "Pacman";
		return type;
	}

	protected void traceBestFood(ArrayList<SimulationObject> fList) {	
		if (fList.size()>0) {	
			// set the 1st item as default target
			SimulationObject target = fList.get(0);
			float targetAttraction = this.getAttraction(target);
			
			// find the closer one
			for (SimulationObject f:fList) if (this.getAttraction(f) > targetAttraction) {
				target = f;
				targetAttraction = this.getAttraction(target);
			}
			
			// make animal follow this target
			this.attractedBy(target);
		}	
	}
	
	protected float getAttraction(SimulationObject target) {
		return target.getSize()*10/PVector.dist(position, target.getPos());
	}
	
	protected abstract boolean eatable(SimulationObject food);
	protected ArrayList<SimulationObject> getTargetList(ArrayList<SimulationObject> fList) {
		ArrayList<SimulationObject> list = new ArrayList<>();
		for (SimulationObject f:fList) if (eatable(f)) list.add(f);
		return list;
	}
	
	protected void drawInfo(Graphics2D g) {
		AffineTransform at = g.getTransform();
		g.translate(position.x, position.y);
		
		String st0 = "Age     : "+ String.format("%d", age/1000);
		String st1 = "Size     : " + String.format("%.2f", size);
		String st2 = "Speed  : " + String.format("%.2f", speedMagnitude);
		String st3 = "Energy : " + String.format("%.2f", energy);
		
		Font f = new Font("Courier", Font.PLAIN, 12);
		FontMetrics metrics = g.getFontMetrics(f);
		
		float textWidth = metrics.stringWidth(st3);
		float textHeight = metrics.getHeight();
		float margin = 12, spacing = 6;
		
		g.setColor(new Color(255,255,255,60));
		if (age < 1200 && (age % 250) < 100) g.setColor(new Color(255,255,255,0));
		g.fillRect((int)(-textWidth/2-margin), 
				(int)(-height*size*.75f - textHeight*5f - spacing*4f - margin*2f), 
				(int)(textWidth + margin*2f), 
				(int)(textHeight*5f + spacing*4f + margin*2f));
		
		g.setColor(Color.blue.darker());
		g.drawString(this.animalType(), -metrics.stringWidth(this.animalType())/2,  -height*size*.75f - margin - (textHeight + spacing)*4f);
		g.setColor(Color.black);
		g.drawString(st0, -textWidth/2, -height*size*.75f - margin - (textHeight + spacing)*3f);
		g.drawString(st1, -textWidth/2, -height*size*.75f - margin - (textHeight + spacing)*2f);
		g.drawString(st2, -textWidth/2, -height*size*.75f - margin - (textHeight + spacing)*1f);
		if (energy < FULL_ENERGY*.3f) g.setColor(Color.red);
		g.drawString(st3, -textWidth/2, -height*size*.75f - margin);
		
		g.setTransform(at);
	}

}
