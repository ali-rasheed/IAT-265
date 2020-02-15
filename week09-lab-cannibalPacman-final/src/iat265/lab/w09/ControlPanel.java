package iat265.lab.w09;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ControlPanel extends JPanel {
	
	private static final long serialVersionUID = 2971317405783062396L;
	private Container center;
	private JLabel pNum, pwNum, rpwNum, cpNum;
	private JTextField pNumField, pwNumField, rpwNumField, cpNumField;
	
	private Container top;
	private JButton addPacman, addPacwoman, addRoyalPacwoman, addCannibalPacman;
	
	private Container bottom;
	private JLabel statusLabel;
	
	public ControlPanel() {
		setComponentsAttributes();
		
		this.setLayout(new BorderLayout());
		//this.add(statusLabel, BorderLayout.SOUTH);
		this.add(center, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
		this.add(top, BorderLayout.NORTH);
	}

	private void setComponentsAttributes() {
		// top
		pNum = new JLabel("Pacman: ");
		pwNum = new JLabel("Pacwoman: ");
		rpwNum = new JLabel("Royal Pacwoman: ");
		cpNum = new JLabel("Cannibal Pacman: ");
		pNumField = new JTextField(5);
		pNumField.setHorizontalAlignment(SwingConstants.CENTER);
		pNumField.setFocusable(false);
		pwNumField = new JTextField(5);
		pwNumField.setHorizontalAlignment(SwingConstants.CENTER);
		pwNumField.setFocusable(false);
		rpwNumField = new JTextField(5);
		rpwNumField.setHorizontalAlignment(SwingConstants.CENTER);
		rpwNumField.setFocusable(false);
		cpNumField = new JTextField(5);
		cpNumField.setHorizontalAlignment(SwingConstants.CENTER);
		cpNumField.setFocusable(false);
		center = new Container();
		center.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		center.add(Box.createHorizontalStrut(2));
		center.add(pNum);
		center.add(pNumField);
		center.add(pwNum);
		center.add(pwNumField);
		center.add(rpwNum);
		center.add(rpwNumField);
		center.add(cpNum);
		center.add(cpNumField);
		
		//center
		addPacman = new JButton("Add Pacman");
		addPacman.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacmanPanel.addPacman();		
			}
		});
		addPacwoman = new JButton("Add Pacwoman");
		addPacwoman.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacmanPanel.addPacwoman();		
			}
		});
		addRoyalPacwoman = new JButton("Add Royal Pacwoman");
		addRoyalPacwoman.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacmanPanel.addRoyalPacwoman();		
			}
		});
		addCannibalPacman = new JButton("Add Cannibal Pacman");
		addCannibalPacman.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacmanPanel.addCannibalPacman();		
			}
		});
		top = new Container();
		top.setLayout(new FlowLayout(FlowLayout.LEADING));
		top.add(addPacman);
		top.add(addPacwoman);
		top.add(addRoyalPacwoman);
		top.add(addCannibalPacman);
		
		// bottom
		statusLabel = new JLabel("Status");
		bottom = new Container();
		bottom.setLayout(new FlowLayout(FlowLayout.LEADING, 12, 5));
		bottom.add(statusLabel);
		bottom.setBackground(Color.DARK_GRAY);
	}
	
	public void update(PacmanPanel p) {
		statusLabel.setText(p.getStatus());
		pNumField.setText(String.format("%d / %d", p.countObject(Pacman.class), p.countTotalObj(Pacman.class)));
		pwNumField.setText(String.format("%d / %d", p.countObject(Pacwoman.class), p.countTotalObj(Pacwoman.class)));
		rpwNumField.setText(String.format("%d / %d", p.countObject(RoyalPacwoman.class), p.countTotalObj(RoyalPacwoman.class)));
		cpNumField.setText(String.format("%d / %d", p.countObject(CannibalPacman.class), p.countTotalObj(CannibalPacman.class)));
	}

}
