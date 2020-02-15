package iat265.week11;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

//import com.sun.glass.ui.Timer;


public class StudentApp extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 791125711820166938L;

	// ----------------------- CONSTANTS --------------------------

	// Default update period of the panel
	protected static final int DEFAULT_UPDATE_PERIOD_IN_MS = 33;

	// Width of control panel (This is how to declare a constant)
	private static final int CONTROL_PANEL_DEFAULT_WIDTH = 240;

	// ----------------------- VARIABLES -------------------------

	// Timer for update this application
	private Timer updateTimer;

	// The main panel
	private MainPanel mainPanel;
	// The control panel
	private ControlPanel controlPanel;

	// List of students
	private ArrayList<Student> studentList;

	// ---------------------- METHODS --------------------------

	// Constructor
	public StudentApp(String title, int width, int height) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Set initial size of the frame
		setPreferredSize(new Dimension(width, height));

		// Set border layout
		setLayout(new BorderLayout());

		// Create main panel and control panel
		mainPanel = new MainPanel(this, width - CONTROL_PANEL_DEFAULT_WIDTH, height);
		controlPanel = new ControlPanel(this, CONTROL_PANEL_DEFAULT_WIDTH, height);

		// Add the panels to the frame
		add(mainPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.EAST);

		// Causes the frame to be sized to fit preferred size
		pack();
		// Show the frame
		setVisible(true);

		// Initialize student list
		studentList = new ArrayList<Student>();

		// Set timer
		updateTimer = new Timer(DEFAULT_UPDATE_PERIOD_IN_MS, this);
		updateTimer.start();
	}

	// Overrides actionPerformed method of the interface ActionListener.
	// Calls update() method of all panels.
	@Override
	public void actionPerformed(ActionEvent e) {
		controlPanel.updateComponent();
		mainPanel.updateComponent();
	}

	// Getter for student panel which allows other classes to access it
	public MainPanel getMainPanel() {
		return mainPanel;
	}

	// Getter for student panel which allows other classes to access it
	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	// Update MainPanel to when student list is refreshed
	public void updateMainPanel() {
		mainPanel.updateStudentList(studentList);
	}

	// --------------------------- MAIN METHOD ------------------------------

	// Default application width
	private static int appWidth = 800;
	// Default application height
	private static int appHeight = 600;

	// Main method to create the frame
	public static void main(String[] args) {
		StudentApp app = new StudentApp("Student Management App", appWidth, appHeight);
	}

}
