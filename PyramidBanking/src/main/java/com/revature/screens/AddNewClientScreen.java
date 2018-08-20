package com.revature.screens;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.account.Account;
import com.revature.account.Transactions;
import com.revature.daos.PyramidDao;
import com.revature.daos.PyramidSerializer;
import com.revature.beans.User;

public class AddNewClientScreen implements Screen {

	private PyramidDao bd = new PyramidSerializer();
	private Scanner scan = new Scanner(System.in);
	private User user;
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private double intialAmount;
	private List<Transactions> transaction = new ArrayList<>();

	@Override
	public Screen start() {
		// get all of the new users information and try to add them to the file
		System.out.println("Enter username");
		username = scan.nextLine();
		System.out.println("Enter first name");
		firstName = scan.nextLine();
		System.out.println("Enter last name");
		lastName = scan.nextLine();
		System.out.println("Enter password");
		password = scan.nextLine();
		System.out.println("Enter amount of initial deposit");
		intialAmount = scan.nextDouble();

		transaction.add(new Transactions("deposit", intialAmount, LocalDateTime.now()));

		user = new User(username, password, firstName, lastName,
				new Account(bd.getUsers().size() + 1, intialAmount, transaction), true);

		if (bd.addUser(user)) {
			System.out.println("\nWelcome " + user.getUsername() + ".");
			return new HomeScreen();
		}

		username = scan.nextLine();

		return this;
	}

}