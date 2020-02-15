package iat265.lab.w08;

import java.util.ArrayList;

import processing.core.PVector;

public abstract class Animal extends SimulationObject {
	
	protected PVector speed;					//position and speed
	protected float maxSpeed;				//speed limit
	protected float energy;					//energy
	protected float FULL_ENERGY = 1000;

	public Animal(float x, float y, float w, float h, float size) {
		super(x, y, w, h, size);
		maxSpeed = Util.random(3, 5);
		speed = Util.randomPVector(maxSpeed);
		energy = FULL_ENERGY;
	}
	
	protected void move() {
		speed.normalize().mult(maxSpeed);
		
		//decrease speed base on size
		float ratio = 1;
		if (size < .5f) ratio = 0.1f + 1.8f*size;
		else if (size < 1) ratio = 1f - 1.8f*(size-0.5f);
		else ratio = .1f;
		speed.mult(ratio);
		
		//apply speed to position
		position.add(speed);
		
		//lose energy
		energy-= FULL_ENERGY/(30*10); //30fps x 10sec living duration
	}

	protected void attractedBy(SimulationObject target) {
		float coef = .2f;	// coefficient of acceleration relative to maxSpeed
		PVector direction = PVector.sub(target.getPos(), position).normalize();
		PVector acceleration = PVector.mult(direction, maxSpeed*coef);
		speed.add(acceleration);
	}
	
	@Override
	public void update(ArrayList<SimulationObject> objList) {
		if (energy <= 0) {
			objList.remove(this);
			return;
		}
		ArrayList<SimulationObject> fList = getTargetList(objList);
		traceBestFood(fList);
		move();
		for (int i = 0; i < fList.size(); i++) 
			if (collides(fList.get(i))) {
				float foodSize = fList.get(i).getSize();
				if (energy < FULL_ENERGY) {
					energy += foodSize*100;
					System.out.format("%s gains energy by %f units %n", animalType(), foodSize*100);
					//PacmanPanel.setStatus(String.format("%s gains energy by %.2f units %n", animalType(), foodSize*100));
				} else {
					size += foodSize*0.01*size;
					System.out.format("%s grows by %.1f%%%n", animalType(), fList.get(i).getSize());
					//PacmanPanel.setStatus(String.format("%s grows by %.1f%%%n", animalType(), fList.get(i).getSize()));
				}		
				objList.remove(fList.get(i));
			}
	}
	
	private String animalType() {
		String type = "unknown animal";
		if (this instanceof SmartPacwoman) type = "Smart Pacwoman";
		else if (this instanceof Pacwoman) type = "Pacwoman";
		return type;
	}

	protected abstract void traceBestFood(ArrayList<SimulationObject> fList);
	protected abstract boolean eatable(SimulationObject food);
	protected ArrayList<SimulationObject> getTargetList(ArrayList<SimulationObject> fList) {
		ArrayList<SimulationObject> list = new ArrayList<>();
		for (SimulationObject f:fList) if (eatable(f)) list.add(f);
		return list;
	}

}
