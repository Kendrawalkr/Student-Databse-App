package studentdatabaseapp;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Database {
	//Tree map of Students in database
	TreeMap<Integer, Student> studentMap = new TreeMap<Integer, Student>();
	
	
	public void databaseMenu() {
		int option = -1;
		while (option !=0) {
			System.out.print("Select from the following options: \n 1- Add Student \n 2- View Student \n(0 to exit)");
			Scanner in = new Scanner(System.in);
			option = in.nextInt();
			switch(option) {
			case 0: option = 0;
				return;
			case 1: addStudent();
				break;
			case 2: viewStudent();
				break;
			case 3: printAll();
			default: break;
			}
		}
	}
	
	private boolean addStudent() {
		Student newStudent = new Student();
		newStudent.viewStudentInfo();
		if (studentMap.put(newStudent.getID(), newStudent) == null) {
			newStudent.viewStudentInfo();
			System.out.println("added");
			return true;
		}
		System.out.println("not added");
		return false;
	}
	
	private void viewStudent() {
		Student found = findStudent();
		if (found != null) {
			found.viewStudentInfo();
			found.menu();
		} else {
			System.out.println("Student not registered");
		}
	}
		
	
	private Student findStudent() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter Student ID#: (enter 0 if unknown)");
		int id = in.nextInt();
		in.nextLine();
		Student found = null;
		if (id == 0) {
			System.out.print("Enter Student Last Name:");
			String last = in.nextLine();
			System.out.print("Enter Student First Name:");
			String first = in.nextLine();
			System.out.print("Enter Student Grade Year:");
			int grade = in.nextInt();

			found = findByName(last, first, grade);		
		}
		else {
			found = findByID(id);
		}
		return found;
	}
	
	private Student findByName(String last, String first, int grade) {
		Student found = null;
		Student toFind = new Student(last, first, grade);
		toFind.viewStudentInfo();
		for (Student s : studentMap.values()) {
			if (toFind.getName().equals(s.getName())) {
				found = s;
				break;
			}
		}
		
		return found;
	}
	
	private Student findByID(int id) {
		return studentMap.get(id);		
	}
	
	public void printAll() {
		Set<Integer> keys = studentMap.keySet();
		for (Student s : studentMap.values()) {
			s.viewStudentInfo();
		}
		
	}
	
}
