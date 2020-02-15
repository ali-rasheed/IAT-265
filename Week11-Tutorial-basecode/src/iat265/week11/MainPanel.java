package iat265.week11;

import java.awt.FlowLayout;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class MainPanel extends BasePanel {
	
	// List of all widgets showing students' information
	private ArrayList<StudentWidget> studentWidgets;
	
	// Constructor
	public MainPanel(StudentApp app, int width, int height) {
		// Calls superclass' constructor
		super(app, width, height);
		
		// Initialize student widgets
		studentWidgets = new ArrayList<StudentWidget>();
		
		// Set padding
		setPadding(24);
		
		// Set grid layout
		setLayout(new FlowLayout(FlowLayout.LEADING, 24, 24));
	}
	
	// Update list of students
	public void updateStudentList(ArrayList<Student> studentList) {
		// Remove old widgets from panel
		for (StudentWidget widget : studentWidgets) {
			remove(widget);
		}
		
		// Clear current widgets and create new ones
		studentWidgets.clear();
		for (Student student : studentList) {
			studentWidgets.add(new StudentWidget(studentApp, 140, 180, student));
		}
		
		// Add widgets to panel
		for (StudentWidget widget : studentWidgets) {
			add(widget);
		}
		
		// Refreshes the panel by validating
		// You must call this, otherwise, nothing changes.
		validate();
	}
	
	// Update this component, called by timer of StudentApp
	@Override
	public void updateComponent() {
		super.updateComponent();
		// Repaint the panel
		repaint();
		// Update all widgets
		for (StudentWidget widget : studentWidgets) {
			widget.updateComponent();
		}
	}
}
