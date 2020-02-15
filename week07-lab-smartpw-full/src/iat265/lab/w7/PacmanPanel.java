package iat265.lab.w7;
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
	private ArrayList<Pacwoman> pList;
	private ArrayList<Food> fList;
	private Timer t;
	private Dimension pnlSize;
	private int MAX_FOOD = 5;
	
	public PacmanPanel(Dimension initialSize) {
		super();
		this.pnlSize = initialSize;
		
		// create new animals
		this.pList = new ArrayList<>();
		this.pList.add(new Pacwoman(pnlSize.width/2, pnlSize.height/2, .3f));
		this.pList.add(new SmartPacwoman(pnlSize.width/2, pnlSize.height/2, .3f));
		
		// create new food instances
		this.fList = new ArrayList<>();
		while (fList.size() < MAX_FOOD) fList.add(Util.randomFood(pnlSize.width/2, pnlSize.height/2, pnlSize.height*Util.random(.35f,.45f)));
		
		t = new Timer(33, this);
		t.start();
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		pnlSize = getSize();
		setBackground(Color.darkGray);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//draw objects
		for (Food f:fList) f.draw(g2);
		for (Pacwoman p:pList) p.draw(g2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Pacwoman p:pList ) p.hunt(fList);
		while (fList.size() < MAX_FOOD) fList.add(Util.randomFood(pnlSize));	
		repaint();
	}
	
	private class MyKeyAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (t.isRunning()) t.stop();
				else t.start();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_UP) t.setDelay(t.getDelay()/2);
			if (e.getKeyCode() == KeyEvent.VK_DOWN) t.setDelay(t.getDelay()*2);
			
		}
		
	}
	
}
