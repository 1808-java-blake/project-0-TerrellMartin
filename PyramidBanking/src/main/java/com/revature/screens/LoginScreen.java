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
		System.out.println("========================================================================");
		System.out.println("|	         Welcome to pyramid banking. 				|");
		System.out.println("| 	      These are the following commands: 			|");
		System.out.print("|		login");
		System.out.print("        Register");
		System.out.print("        exit			|");
		System.out.println();
		System.out.println("========================================================================");
		int r = 7;
		int spaces = r*2 - 2;
		for(int i = 0; i < r; i++){
			
			for(int j = 0; j < spaces; j++){
				System.out.print(" ");
			}
			
			spaces = spaces - 1;
			for (int j = 0; j <= i; j++){
				System.out.print("  $ ");
			}
			System.out.println();
		}
		
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
				if (user.getPyramidAccount().getAccountNumber() == 0)
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
