package iat265.week11;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student {
	
	// Conventional string format of a date
	private static final String DEFAULT_STRING_FORMAT_OF_DATE = "mm/dd/yyyy";
	// The following object helps us to convert String to Date and vice versa
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(DEFAULT_STRING_FORMAT_OF_DATE);
	
	// If a class represents of set of information
	// it is not a bad practice to make its fields public
	// for simple and convenient access.
	public String firstName;
	public String lastName;
	public int studentID;
	public float height;
	public float weight;
	public Date dateOfBirth;
	
	

	// Constructor from given information
	public Student(String firstName, String lastName, int studentID, float height, float weight, Date dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentID = studentID;
		this.height = height;
		this.weight = weight;
		this.dateOfBirth = dateOfBirth;
	}

	// Create a instance of Student by parsing a string 
	// of format firstName,lastName,studentID,height,weight,month/day/year.
	// Of course, you can make a constructor instead of using this static method.
	// However, for converting a string into another type, static method is preferred.
	public static Student parseString(String str) {
		// Split the string into parts separated by commas
		String[] parts = str.split(",");
		
		// The first and second parts are first and last name respectively
		String firstName = parts[0];
		String lastName = parts[1];
		// Gets studentID from third part and convert it to integer
		int studentID = Integer.parseInt(parts[2]);
		// Gets height and weight with conversion from string to float
		float height = Float.parseFloat(parts[3]);
		float weight = Float.parseFloat(parts[4]);
		
		// Gets date of birth
		// Convert string of format mm/dd/yyyy to date
		// Give dateOfBirth a default value as Date()
		Date dateOfBirth = new Date();
		try {
			// The following command may fail if the given string does not have the expected format.
			// That's why we need to surround this comment with try catch
			dateOfBirth = DATE_FORMAT.parse(parts[5]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Create an instance of Student and return
		return new Student(firstName, lastName, studentID, height, weight, dateOfBirth);
	}
	
	// Getter for full name
	public String getFullName() {
		// Note that: %s represents a string and will be substituted by a given string.
		return String.format("%s %s", firstName, lastName);
	}

	// Gets string representing date of birth in the form of mm/dd/yyyy
	public String getDateOfBirth() {
		// Convert date to string
		return DATE_FORMAT.format(dateOfBirth);
	}

	@Override
	public String toString() {
		// Prints all information fields separated by commas.
		// Note that: %d will be substituted by an integer.
		//            %.[x]f will be substituted by a float/double. [x] is the number of decimal digits.
		return String.format("%s,%s,%d,%.3f,%.3f,%s", firstName, lastName, studentID, height, weight, getDateOfBirth());
	}
}
