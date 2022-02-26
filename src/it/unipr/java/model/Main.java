package it.unipr.java.model;

import java.util.Scanner;

public class Main {	

	public static void main(String[] args) {
		
		System.out.println("Progetto Finale - Circolo Velico\n");
		
		try (Scanner input = new Scanner(System.in)) {
			input.useDelimiter("\n");
			
			SailingClub club = new SailingClub();
			
			System.out.print("Email: ");
			String email = input.nextLine();
			
			System.out.print("Password: ");
			String password = input.nextLine();
			
			System.out.println();
			
			User loggedUser = club.login(email, password);
			
			if (loggedUser instanceof Member) {
				System.out.println("The logged in user is a member of the sailing club.");
			} else if (loggedUser instanceof Employee) {
				Employee employee = (Employee) loggedUser;
				if (employee.isAdministrator()) {
					System.out.println("The logged in user is an administrator of the sailing club.");
				} else {
					System.out.println("The logged in user is an employee of the sailing club.");
				}
			}
		}
	}

}
