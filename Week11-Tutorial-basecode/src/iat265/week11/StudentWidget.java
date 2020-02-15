package iat265.week11;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class StudentWidget extends BasePanel {

	// Font for full name. student ID
	private static final Font FULL_NAME_FONT = new Font("Arial", Font.BOLD, 13);
	private static final Font STUDENT_ID_FONT = new Font("Arial", Font.ITALIC | Font.BOLD, 12);

	// Font used by other labels
	private static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 12);

	// Keeps a reference to the given student
	private Student student;
	
	// Labels
	private JLabel fullNameLabel;
	private JLabel studentIDLabel;
	private JLabel heightLabel;
	private JLabel weightLabel;
	private JLabel dobLabel;

	// Avatar panel
	private AvatarPanel avatarPanel;

	// Constructor
	public StudentWidget(StudentApp app, int width, int height, Student st) {
		// Calls constructor of superclass
		super(app, width, height);
		student = st;

		// Sets padding, background, and border layout
		setPadding(12);
		setBackground(new Color(80, 80, 80, 60));
		// Use Box Layout to align elements in 1 column
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Adds JLabel to show first name and last name
		fullNameLabel = new JLabel(student.getFullName(), SwingConstants.CENTER);
		fullNameLabel.setFont(FULL_NAME_FONT);
		// Sets text color
		fullNameLabel.setForeground(Color.BLUE);
		// Center this label
		fullNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		// Adds to this widget
		add(fullNameLabel);

		// Adds JLabel for student ID
		studentIDLabel = new JLabel(String.format("%d", student.studentID), SwingConstants.CENTER);
		studentIDLabel.setFont(STUDENT_ID_FONT);
		studentIDLabel.setForeground(Color.BLACK);
		studentIDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(studentIDLabel);

		// Add avatar panel
		avatarPanel = new AvatarPanel(width, 80);
		add(avatarPanel);

		// Add JLabel for height
		heightLabel = new JLabel(String.format("Height: %.2fm", student.height), SwingConstants.CENTER);
		heightLabel.setFont(NORMAL_FONT);
		heightLabel.setForeground(Color.BLACK);
		heightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(heightLabel);

		// Add JLabel for weight
		weightLabel = new JLabel(String.format("Weight: %.2fkg", student.weight), SwingConstants.CENTER);
		weightLabel.setFont(NORMAL_FONT);
		weightLabel.setForeground(Color.BLACK);
		weightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(weightLabel);

		dobLabel = new JLabel(String.format("Birthday: %s", student.getDateOfBirth()), SwingConstants.CENTER);
		dobLabel.setFont(NORMAL_FONT);
		dobLabel.setForeground(Color.BLACK);
		dobLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(dobLabel);
	}
	
	// Update the widget
	@Override
	public void updateComponent() {
		super.updateComponent();
		// Repaints the avatar
		avatarPanel.repaint();
	}

	// Inner class to draw avatar of student
	private class AvatarPanel extends JPanel {

		public AvatarPanel(int width, int height) {
			setPreferredSize(new Dimension(width, height));
		}

		// Overrides paint component.
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// Set transparent background
			setBackground(new Color(0, 0, 0, 0));
			
			// Max height for the avatar, it should not occupy the whole panel
			// to avoid overlapping with other components of the widget
			int maxHeight = (int) (getHeight() * 0.8);
			// Max width of the avatar. It represents the weight of student.
			int maxWidth = (int) (getWidth() * 0.6);
			
			// Compute height of the avatar, which is proportional to the height of the student
			// We assume 2 meter ~ 100% max height
			int height = (int) (maxHeight * (student.height / 2.0));
			// Limits by max height
			height = Math.min(height, maxHeight);
			
			// Compute width
			int width = (int) (maxWidth * (student.weight / 100));
			width = Math.min(width, maxWidth);
			
			// Draw an red oval at center
			int x = (getWidth() - width) / 2;
			int y = (getHeight() - height) / 2;
			g.setColor(Color.RED);
			g.fillOval(x, y, width, height);
			
			// Draw eyes
			g.setColor(randomColor());
			int eyeSize = (int) (maxWidth * 0.1);
			g.fillOval(x + width / 2 - width / 10 - eyeSize, y + height / 6, eyeSize, eyeSize);
			g.fillOval(x + width / 2 + width / 10, y + height / 6, eyeSize, eyeSize);
			
			// Draw mounth
			g.setColor(Color.WHITE);
			g.drawArc(x + width / 4, y + width / 4, width / 2, height / 2, 0, -180);
			
		}
		
		// Random color
		private Color randomColor() {
			Random r = new Random();
			return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
		}
	}
}
