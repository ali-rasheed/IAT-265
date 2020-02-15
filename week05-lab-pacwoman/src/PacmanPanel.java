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

import processing.core.PVector;

public class PacmanPanel extends JPanel
	implements ActionListener {

	private ArrayList<Pacwoman> pList;

	private ArrayList<Pacman> pmList;

	private ArrayList<Food> fList;

	private Timer t;

	private Dimension pnlSize;


	public PacmanPanel(Dimension initialSize) {

		super();
		this.pnlSize = initialSize;

		this.pList = new ArrayList<>();
		for (int i = 0; i < 5; i++)
			pList.add(
				new Pacwoman(
					Util.random(50, pnlSize.width - 50), // x
					Util.random(50, pnlSize.height - 50), // y
					Util.random(.1, .5)));
		this.pmList = new ArrayList<>();
		for (int i = 0; i < 5; i++)
			pmList.add(
				new Pacman(
					Util.random(50, pnlSize.width - 50), // x
					Util.random(50, pnlSize.height - 50), // y
					Util.random(.1, .5)));
		// size
		this.fList = new ArrayList<>();
		for (int i = 0; i < 3; i++)
			fList.add(new Food(
				Util.random(50, pnlSize.width - 50), // x
				Util.random(50, pnlSize.height - 50), // y
				Util.random(1, 5))); // size

		// action listener: mouse and time
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
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		// draw objects
		for (Food f : fList)
			f.draw(g2);
		for (Pacwoman p : pList)
			p.draw(g2);
		for (Pacman p : pmList)
			p.draw(g2);
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		for (Pacwoman p : pList) {
			Food target = findClosestFood(p);
			if (target != null)
				p.attractedBy(target);
			p.move();

			for (int i = 0; i < fList.size(); i++)
				if (p.collides(fList.get(i))) {
					fList.remove(i);
					fList.add(new Food(
						Util.random(50, pnlSize.width - 50),
						Util.random(50,
							pnlSize.height - 50),
						Util.random(1, 5)));
				}

			for (int i = 0; i < pList.size(); i++)
				if (p != pList.get(i)
					&& p.collides(pList.get(i))) {
					if (p.getSize() < pList.get(i)
						.getSize())
						p.distractedBy(pList.get(i));
					else
						pList.get(i).distractedBy(p);
				}

			p.checkCollision(getSize());
		}
		for (Pacman p : pmList) {
			Food target = findClosestFood(p);
			if (target != null)
				p.attractedBy(target);
			p.move();

			for (int i = 0; i < fList.size(); i++)
				if (p.collides(fList.get(i))) {
					fList.remove(i);
					fList.add(new Food(
						Util.random(50, pnlSize.width - 50),
						Util.random(50,
							pnlSize.height - 50),
						Util.random(1, 5)));
				}

			for (int i = 0; i < pmList.size(); i++)
				if (p != pmList.get(i)
					&& p.collides(pList.get(i))) {
					if (p.getSize() < pList.get(i)
						.getSize())
						p.distractedBy(pList.get(i));
					else
						pList.get(i).distractedBy(p);
				}

			p.checkCollision(getSize());
		}

		repaint();
		System.out.println(pmList.size());
		System.out.println(pList.size());

	}


	private Food findClosestFood(Pacwoman p) {

		Food closestFood = null;

		if (fList.size() > 0) {
			closestFood = fList.get(0);
			float closestDist = PVector.dist(p.getPos(),
				closestFood.getPos());
			for (Food f : fList)
				if (PVector.dist(p.getPos(),
					f.getPos()) < closestDist) {
					closestFood = f;
					closestDist = PVector.dist(p.getPos(),
						closestFood.getPos());
				}
		}

		return closestFood;
	}


	private Food findClosestFood(Pacman p) {

		Food closestFood = null;

		if (fList.size() > 0) {
			closestFood = fList.get(0);
			float closestDist = PVector.dist(p.getPos(),
				closestFood.getPos());
			for (Food f : fList)
				if (PVector.dist(p.getPos(),
					f.getPos()) < closestDist) {
					closestFood = f;
					closestDist = PVector.dist(p.getPos(),
						closestFood.getPos());
				}
		}

		return closestFood;
	}


	private class MyMouseAdapter extends MouseAdapter {

		public void mousePressed(MouseEvent e) {

			boolean hitAFood = false;

			for (int i = 0; i < fList.size(); i++) {
				if (fList.get(i).checkMouseHit(e)) {
					hitAFood = true;
					if (e.isShiftDown())
						fList.get(i)
							.setColor(Util.randomColor());
					if (e.isControlDown())
						fList.remove(i);
				}
			}

			if (!hitAFood && !e.isControlDown()
				&& e.isShiftDown())
				fList.add(new Food(e.getX(), // x
					e.getY(), // y
					Util.random(1, 5))); // size
		}
	}

}
