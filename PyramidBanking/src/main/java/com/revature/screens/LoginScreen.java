package com.revature.screens;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import com.revature.account.Account;
import com.revature.account.Transactions;
//import com.revature.account.Transactions;
import com.revature.daos.PyramidDao;
import com.revature.daos.PyramidSerializer;
import com.revature.beans.User;



public class LoginScreen implements Screen {
//	private Scanner scan = new Scanner(System.in);
//	private UserDao ud = UserDao.currentUserDao;

		//number of rows for pyramid
//		int r = 5;
//		int spaces = r*2 - 2;
//		for(int i = 0; i < r; i++){
//			
//			for(int j = 0; j < spaces; j++){
//				System.out.print(" ");
//			}
//			
//			spaces = spaces - 1;
//			for (int j = 0; j <= i; j++){
//				System.out.print("$ ");
//			}
//			System.out.println();
//		}
//		System.out.println("Welcome to Pyramid Banking.");
//		System.out.println("Type one of the following LoginScreen Commands: ");
//		System.out.println("Register         Login           Exit");
//		String s = scan.nextLine();
//		if ("register".equalsIgnoreCase(s)) {
//			return new RegisterUserScreen();
//		}
//		if ("exit".equalsIgnoreCase(s)){
//			System.out.println("Have a nice day! Goodbye");
//		    System.exit(1);
//		}
//		if ("login".equalsIgnoreCase(s)){
//			//proceed with the program.
//			System.out.println();
//		}
//		else {
//			System.out.println("Incorrect command. Try again.");
//			return new LoginScreen();
//		}
//		
//		System.out.println("Enter your username: ");
//		String username = scan.nextLine();
//		System.out.println("Enter Password: ");
//		String password = scan.nextLine();
//
//		User currentUser = ud.findByUsernameAndPassword(username, password);
//		if (currentUser != null) {
//			return new HomeScreen();
//		}
//
//		System.out.println("unable to login");
//		return this;
		private PyramidDao bd = new PyramidSerializer();
		private Scanner scan = new Scanner(System.in);
		private List<Transactions> transactions;
		private List<User> users = new ArrayList<>();
		private User user;
		@Override
		public Screen start() {
	
	
		User admin = new User("admin", "admin", "admin", "admin", new Account(0, 0, null), false);
		bd.addUser(admin);

		users = bd.getUsers();
		System.out.println("Welcome to pyramid banking. These are the following commands: ");
		System.out.print("login");
		System.out.print("        Register");
		System.out.print("        exit");
		System.out.println();
		String choice = scan.nextLine().toLowerCase();
		switch (choice) {
		case "login":
			System.out.println("Enter your username:");
			String username = scan.nextLine();
			System.out.println("Enter your password:");
			String password = scan.nextLine();

			if (checkUserCridentials(users, username, password, bd)) {
				user = HomeScreen.getCurrentUser(bd);
				// IF the bank account is 0 then the admin has logged in go the the admin page
				if (user.getBankAccount().getAccountNumber() == 0)
					return new AdminScreen();
				else
					return new HomeScreen();
			} else
				System.out.println("You've entered the wrong password. Please try again.");
				//return new LoginScreen();

			//break;
					case "register":
			return new AddNewClientScreen();
			
		case "exit":
			System.exit(1);
		default:
			break;
		}

		return this;
	}

	public boolean checkUserCridentials(List<User> users, String username, String password, PyramidDao bd) {
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				System.out.println("Welcome " + username);
				
				if (bd.userLoggedIn(user))
					return true;
			}
		}

		return false;
	}

}
