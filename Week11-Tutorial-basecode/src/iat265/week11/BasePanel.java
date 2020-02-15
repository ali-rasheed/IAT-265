package iat265.week11;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

// Abstract base class for all panels in student application.
// This class helps to void duplicate code in subclasses (MainPanel, ControlPanel, etc.)
@SuppressWarnings("serial")
public abstract class BasePanel extends JPanel { 

	// Refers to the student application containing this panel.
	// This reference allows this class to access other panels.
	protected StudentApp studentApp;
	
	// Constructor
	public BasePanel(StudentApp app, int width, int height) {
		studentApp = app;
		// Sets preferred width and height
		setPreferredSize(new Dimension(width, height));
	}
	
	// Set padding, reserved equal spaces at 4 edges of the panel.
	public void setPadding(int padding) {
		setBorder(new EmptyBorder(padding, padding, padding, padding));
	}
	
	// Update method, which is called to update this component (this panel).
	// Does noting by default, but can be overridden by subclasses.
	public void updateComponent() {
	}
}
