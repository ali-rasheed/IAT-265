package iat265.lab.w09;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PacmanPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private static ArrayList<SimulationObject> objList;
	private static Timer t;
	private static Dimension pnlSize;
	private static int MAX_FOOD  = 3;
	private static int MIN_PER_KIND = 1;
	private static int pacmanCount;
	private static int pacwomanCount;
	private static int royalPacwomanCount;
	private static int cannibalPacmanCount;
	private static String status = "Status";
	private static ControlPanel cPanel;
	
	public PacmanPanel(ControlPanel p) {
		super();
		cPanel = p;
		pnlSize = new Dimension(1060,600);
		this.setPreferredSize(pnlSize);
		
		// create new animals
		objList = new ArrayList<>();
		for (int i = 0; i < MAX_FOOD; i++) objList.add(Util.randomFood(pnlSize));
		addPacman();
		addPacwoman();
		addRoyalPacwoman();
		addCannibalPacman();
		
		t = new Timer(33, this);
		t.start();
		this.addKeyListener(new MyKeyAdapter());
		this.setFocusable(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		pnlSize = getSize();
		setBackground(Color.darkGray);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//draw objects
		for (SimulationObject obj:objList ) obj.draw(g2);
		
		//update panel
		cPanel.update(this);
	}
	
	public static void setStatus(String st) {
		status = st;
	}
	
	public String getStatus() {
		return status;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < objList.size(); i++) objList.get(i).update(objList);	
		respawn();
		repaint();
	}
	
	private void respawn() {
		while (countObject(Food.class) < MAX_FOOD) objList.add(Util.randomFood(pnlSize));
		while (countObject(Pacman.class) < MIN_PER_KIND) addPacman();
		while (countObject(Pacwoman.class) < MIN_PER_KIND) addPacwoman();
		while (countObject(RoyalPacwoman.class) < MIN_PER_KIND) addRoyalPacwoman();
		while (countObject(CannibalPacman.class) < MIN_PER_KIND) addCannibalPacman();
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (t.isRunning()) t.stop();
				else t.start();
			}
		}
	}

	public int countObject(Class<?> className) {
		if (className == Pacman.class) return Util.countObject(className, objList) - Util.countObject(CannibalPacman.class, objList);
		if (className == Pacwoman.class) return Util.countObject(className, objList) - Util.countObject(RoyalPacwoman.class, objList);
		return Util.countObject(className, objList);
	}
	
	public int countTotalObj(Class<?> className) {
		if (className == Pacman.class) return pacmanCount;
		if (className == Pacwoman.class) return pacwomanCount;
		if (className == RoyalPacwoman.class) return royalPacwomanCount;
		if (className == CannibalPacman.class) return cannibalPacmanCount;
		return -1;
	}
	
	public static void addPacman() {
		objList.add(Util.randomPacman(pnlSize));
		pacmanCount++;
	}
	
	public static void addPacwoman() {
		objList.add(Util.randomPacwoman(pnlSize));
		pacwomanCount++;
	}
	
	public static void addRoyalPacwoman() {
		objList.add(Util.randomRoyalPacwoman(pnlSize));
		royalPacwomanCount++;
	}
	
	public static void addCannibalPacman() {
		objList.add(Util.randomCannibalPacman(pnlSize));
		cannibalPacmanCount++;
	}
	
}
