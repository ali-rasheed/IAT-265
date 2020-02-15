package iat265.week11;

import java.awt.Color;

import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class ControlPanel extends BasePanel {

	// Constructor
	public ControlPanel(StudentApp app, int width, int height) {
		// Calls superclass' constructor
		super(app, width, height);

		// Sets padding, background, and border layout
		setPadding(24);
		setBackground(Color.LIGHT_GRAY);
		// Use Box Layout to align elements in 1 column
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

}
