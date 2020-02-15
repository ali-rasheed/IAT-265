import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PacmanPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Pacwoman> pList;
	private ArrayList<Food> fList;
	private Timer t;
	private Dimension pnlSize;
	
	public PacmanPanel(Dimension initialSize) {
		super();
		this.pnlSize = initialSize;
		
		// create new animals
		this.pList = new ArrayList<>();
		for (int i = 0; i < 5; i++) pList.add(
				new Pacwoman(	Util.random(50, pnlSize.width-50),
								Util.random(50, pnlSize.height-50),
								Util.random(.1,.5)));
		
		// create new food instances
		this.fList = new ArrayList<>();
		for (int i = 0; i < 3; i++) fList.add(new Food(	Util.random(50, pnlSize.width-50), 
														Util.random(50, pnlSize.height-50),
														Util.random(1,5)));
		
		//action listener: mouse and time
		addMouseListener(new MyMouseAdapter());
		t = new Timer(33, this);
		t.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		pnlSize = getSize();
		setBackground(Color.blue.darker().darker());
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//draw objects
		for (Food f:fList) f.draw(g2);
		for (Pacwoman p:pList) p.draw(g2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Pacwoman p:pList ) {
			// hunt
			p.traceClosestFood(fList);				
			
			// eat
			for (int i = 0; i < fList.size(); i++) if (p.collides(fList.get(i)))
					fList.remove(i);
			
			// animal-animal collision check
//			for (Pacwoman p2:pList) if (p2 != p && p2.collides(p))
//				System.out.println("hit!");
			
			p.checkCollision(pnlSize);
			p.move();
		}
	
		repaint();
	}
	
	
	

	private class MyMouseAdapter extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			
			if (e.getButton() == 3 && e.isShiftDown()) {
				genPacwoman(5);
				return;
			}
			
			boolean hitAFood = false;
			
			for (int i = 0; i < fList.size(); i++) {
				if (fList.get(i).checkMouseHit(e)) { 
					hitAFood = true;
					if (e.isShiftDown()) fList.get(i).setColor(Util.randomColor());
					if (e.isControlDown()) fList.remove(i);	
				}
			}
			
			if (!hitAFood && !e.isControlDown() && e.isShiftDown()) 
				fList.add(new Food(e.getX(), e.getY(), Util.random(1,5)));
		}

		private void genPacwoman(int n) {
			for (int i = 0; i < n; i++)
				fList.add(new Food(Util.random(50, pnlSize.width-50),
						Util.random(50, pnlSize.height-50),
						Util.random(1,5)));
		}
	}
	
}
