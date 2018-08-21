package com.revature.screens;

import java.util.Scanner;

import com.revature.daos.PyramidDao;
import com.revature.daos.PyramidSerializer;
import com.revature.daos.UserDao;
import com.revature.beans.User;

public class AdminScreen implements Screen {

	private PyramidDao bd = new PyramidSerializer();
	private UserDao ud = UserDao.currentUserDao;
	private Scanner scan = new Scanner(System.in);

	@Override
	public Screen start() {
		// TODO Auto-generated method stub
		User user = HomeScreen.getCurrentUser(bd);

		System.out.println("\nPress 1 to view all transactions.");
		System.out.println("Press 2 to Logout.");
		System.out.println("Press 3 to delete user from database.");
		String input = scan.nextLine();

		switch (input) {
		case "1":
			bd.viewAllTransactions(user);
			break;
		case "2":
			bd.userLogout(user);
			return new LoginScreen();
		case "3":
			ud.deleteUser(user);
			return new AdminScreen();
		default:
			break;
		}

		return this;
	}

}