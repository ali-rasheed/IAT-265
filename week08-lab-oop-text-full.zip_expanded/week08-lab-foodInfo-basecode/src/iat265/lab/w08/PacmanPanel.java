package iat265.lab.w08;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PacmanPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<SimulationObject> objList;
	private Timer t;
	private Dimension pnlSize;
	private int MAX_FOOD  = 3;
	@SuppressWarnings("unused")
	private static String status = "Status";
	
	public PacmanPanel(Dimension initialSize) {
		super();
		this.pnlSize = initialSize;
		
		// create new animals
		this.objList = new ArrayList<>();
		this.objList.add(new Pacwoman(pnlSize.width/2, pnlSize.height/2, .25f));
		this.objList.add(new SmartPacwoman(pnlSize.width/2, pnlSize.height/2, .3f));
		for (int i = 0; i < MAX_FOOD; i++) objList.add(Util.randomFood(pnlSize));
		
		t = new Timer(33, this);
		t.start();
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
	}
	
	public static void setStatus(String st) {
		status = st;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < objList.size(); i++) objList.get(i).update(objList);	
		if (Util.countFood(objList) < MAX_FOOD) objList.add(Util.randomFood(pnlSize));
		repaint();
	}
	
}
