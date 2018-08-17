package com.revature.screens;

import java.util.List;
import java.util.Scanner;

import com.revature.account.Transactions;
import com.revature.daos.PyramidDao;
import com.revature.daos.PyramidSerializer;
import com.revature.beans.User;

public class HomeScreen implements Screen {

	private PyramidDao bd = new PyramidSerializer();
	private Scanner scan = new Scanner(System.in);
	private List<Transactions> transactions;
	
	@Override
	public Screen start() {

		User user = getCurrentUser(bd);
		System.out.println("========================================================");
		System.out.println("|		Press 'withdraw' for withdrawl.		|");
		System.out.println("|	Press 'deposit' for deposit.			|");
		System.out.println("|	Press 'view' to view balance.			|");
		System.out.println("|	Type 'transactions' to view transactions.	|");
		System.out.println("|	Type 'logout' to Log out			|");
		System.out.println("========================================================");

		String choice = scan.nextLine();

		switch (choice) {
		//Withdrawal money from the users account.
		case "withdraw":
			bd.witdrawal(user);
			break;
		//deposit money into the users account
		case "deposit":
			bd.deposit(user);
			break;
		//display the users current balance
		case "view":
			bd.viewBalance(user);
			break;
		//list all of the users transactions since the account was created
		case "transactions":
			transactions = bd.getTransactions(user);
			for (Transactions transaction : transactions) {
				System.out.printf("\nA %s for the amount of $%,.2f was made on %tB %<te, %<tY at %<tH:%<tM %<Tp",
						transaction.getType(), transaction.getAmount(), transaction.getDate());
			}
			System.out.println();
			break;
		//log the user out
		case "logout":
			bd.userLogout(user);
			return new LoginScreen();

		default:
			break;
		}

		return this;
	}

	//this will get the current user that is logged in
	public static User getCurrentUser(PyramidDao bd) {
		List<User> users = bd.getUsers();

		for (User u : users) {
			if (u.isLoggedIn() == true)
				return u;
		}

		return null;
	}


}
