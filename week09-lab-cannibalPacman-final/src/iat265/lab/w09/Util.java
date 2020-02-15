package iat265.lab.w09;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import processing.core.PVector;

public class Util {

	public static float random(double min, double max) {
		return (float) (Math.random()*(max-min)+min);
	}
	
	public static float random(double max) {
		return (float) (Math.random()*max);
	}
	
	public static Color randomColor() {
		int r = (int) random(255);
		int g = (int) random(255);
		int b = (int) random(255);
		
		return new Color(r,g,b);
	}
	
	public static PVector randomPVector(int maxX, int maxY) {		
		return new PVector((float)random(maxX), (float)random(maxY));	
	}
	
	public static PVector randomPVector(float magnitude) {
		return PVector.random2D().mult(magnitude);
	}
	
	public static Food randomFood(Dimension pnlSize) {
		return new Food(	Util.random(50, pnlSize.width-50), 
						Util.random(50, pnlSize.height-50),
						Util.random(1,5));
	}
	
	public static Pacman randomPacman(Dimension pnlSize) {
		return new Pacman(Util.random(100, pnlSize.width-100), 
				Util.random(100, pnlSize.height-100), Util.random(3,7));
	}
	
	public static Pacwoman randomPacwoman(Dimension pnlSize) {
		return new Pacwoman(Util.random(100, pnlSize.width-100), 
				Util.random(100, pnlSize.height-100), Util.random(3,7));
	}
	
	public static RoyalPacwoman randomRoyalPacwoman(Dimension pnlSize) {
		return new RoyalPacwoman(Util.random(100, pnlSize.width-100), 
				Util.random(100, pnlSize.height-100), Util.random(3,7));
	}
	
	public static CannibalPacman randomCannibalPacman(Dimension pnlSize) {
		return new CannibalPacman(Util.random(100, pnlSize.width-100), 
				Util.random(100, pnlSize.height-100), Util.random(2,5));
	}
	
	public static int countObject(Class<?> className, ArrayList<SimulationObject> objList) {
		int i = 0;
		for (SimulationObject obj:objList) if (className.isInstance(obj)) i++;
		return i;
	}

}
