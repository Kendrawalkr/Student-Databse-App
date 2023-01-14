package studentdatabaseapp;

import java.util.Scanner;
//import.java.lang.Math;	// import Absolute value abs()
import java.util.ArrayList; // import the ArrayList class

public class Student {
	// Data members

	private String FirstName;
	private String LastName;
	private int Gradelvl;
	private int StudentID;
	private ArrayList<Integer> courses = new ArrayList<Integer>(MAXCOURSES);
	private int TuitionBalance;
	private ArrayList<Double> payments = new ArrayList<Double>();
	
	private static int MAXCOURSES = 10;
	private double CourseCost = 500.00; // default course tuition
	private static int newID =100;
	
	//constructor: Student name and year - get input from user
	public Student() {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter student's first name: ");
		FirstName = in.nextLine();
		
		System.out.print("Enter student's last name: ");
		LastName = in.nextLine();
		
		while (Gradelvl == 0) {
			System.out.print("Enter student grade level:\n  9 - Freshman\n 10 - Sophomore\n 11 - Junior\n 12 - Senior\n");
			int grade = in.nextInt();
			if (grade >= 9 && grade <= 12) {
				this.Gradelvl = grade;
			}
			else {
				System.out.print("Incorrect Grade value, please re-enter:" );
			}
		}
		setID();
	}
	
	// constructor for search by name
	public Student(String last, String first, int year) {
		LastName = last;
		FirstName = first;
		Gradelvl = year;
	}
	
	public String getName() {
		return LastName + FirstName;
	}
	
	public int getGrade() {
		return Gradelvl;
	}
	
	public int getID() {
		return StudentID;
	}
	
	
	// menu selection for different functions
	public void menu() {
		int input = -1;
		while (input !=0) {
			System.out.print("Please enter 1 - 4 from the following options: \n 1- Enroll Courses \n "
					+ "2- Drop Courses \n 3- View Courses \n 4- Tuition \n 5- Student Info \n (0 to exit)");
			Scanner in = new Scanner(System.in);
			int option = in.nextInt();
			switch (option) {
			case 0: return;
			case 1: enroll();
				break;
			case 2: dropCourse();
				break;
			case 3: viewCourses();
				break;
			case 4: tuitionMenu();
				break;
			case 5: studentPrintout();
				break;
			}
		}
		return;		
	}
	
	// Generate 5 digit ID beginning with grade level
	private int setID() {
		//generate based on name and grade
		String ID;
		newID++;
		if (Gradelvl == 9) {
			ID =  Gradelvl + "0" + newID;
		}		
		else {
			ID = Gradelvl + "" + newID ;
		}
		int IDnum = Integer.parseInt(ID);
		if (IDnum >= 10000 && IDnum <= 99999) {
			this.StudentID = IDnum;
		}
		return IDnum;
	}
	
	// Enroll in Courses
	public void enroll() {
		// loop, user enters 0 to exit
		int course = -1;
		// loop start
		while (course != 0) {
			viewCourses();
			System.out.println("Enter 4 digit course number to enroll (0 to quit):");
			Scanner in =new Scanner(System.in);
			course = in.nextInt();
			if (course >= 1000 && course <= 9999) {
				if (this.courses.size() >= 10) {
					System.out.println("Error! Maximum number of courses enrolled, could not add course " + course);
					continue;
				}
				else if (courses.contains(course)) {
					System.out.println("Error: Course is already registered");
					continue;
				}
				else if(this.courses.add(course)) {
					this.TuitionBalance += this.CourseCost;
				}
			}
			else if(course == 0) {
				break;
			}
			else {
				System.out.println("Error! Invalid course number");
			}

		}
		return;
		
	}
	
	// Drop course
	public void dropCourse() {
		int course = -1;
		while (course != 0) {
			//check for any courses registered
			if (!viewCourses()) {
				return;
			}
			System.out.println("Enter 4 digit course number to drop (0 to quit):");
			Scanner in =new Scanner(System.in);
			course = in.nextInt();
			if (course >= 1000 && course <= 9999) {
				if (!this.courses.remove((Integer)course)){
					System.out.println("Not enrolled in course " + course + ": Could not drop");
					continue;
				}
				else {
					this.TuitionBalance -= this.CourseCost;
				}
			}
			else if(course == 0) {
				break;
			}
			else {
				System.out.println("Error! Invalid course number");
			}
		}
		return;

	}
	
	//View current courses
	public boolean viewCourses() {
		//check for any courses registered
		if (this.courses.size() == 0) {
			System.out.println("No courses registered");
			return false;
		}
		System.out.println("\n Current Courses:");
		for (int n : this.courses) {
			System.out.println(n);
		}
		return true;
	}
	
	//View balance and pay tuition
	public void tuitionMenu() {
		int input = -1;
		while (input != 0) {
			System.out.println("Would you like to: \n1- View Balance \n2- Make Payment \n3- View Details \n (0 to exit)");
			Scanner in = new Scanner(System.in);
			int option = in.nextInt();
			switch(option) {
			case 0: return;
			case 1: viewBalance();
				break;
			case 2: System.out.println("Please enter payment amount: \n$" );
				double pay = in.nextDouble();
				payTuition(pay);
				break;
			case 3: tuitionDetails();
				break;
			}
		}
	}
	
	private void viewBalance() {
		System.out.println("Current balance: " + this.TuitionBalance);
		if(TuitionBalance < 0) {
			System.out.println("(Account Overpayment: $" + Math.abs(TuitionBalance) +")");
		}
	}
	
	private void payTuition(double payment) {
		if (payment > 0 && payment <= this.TuitionBalance) {
			payments.add(payment);
			this.TuitionBalance -= payment;
		}
		else {
			System.out.println("$" + payment + " is an invalid amount for current balance of $" + this.TuitionBalance);
		}
		return;
	}
	
	private void tuitionDetails() {
		//check for any courses registered
				if (this.courses.size() == 0) {
					System.out.println("$0.00 - No courses registered");
					return;
				}
				System.out.println("\n Current Courses:");
				for (int n : this.courses) {
					System.out.println(n + " $" + this.CourseCost);
				}
				System.out.println("      Payments:");
				for (double pay : payments) {
					System.out.println("- $" + pay);
				}
				viewBalance();
				return;
	}
	
	
	//Student printout
	public void studentPrintout() {
		System.out.print("      Student Information \n \"Student ID: " + StudentID + " Name: " + LastName + ", " + FirstName);
		viewCourses();
		viewBalance();
		return;
	}
	
	public void viewStudentInfo() {
		System.out.print(StudentID + " " + LastName + ", " + FirstName + '\n');
	}
	
}
