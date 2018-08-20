package com.revature.daos;

import java.util.List;

import com.revature.account.Transactions;
import com.revature.beans.User;

public interface PyramidDao {

	boolean addUser(User user);

	boolean witdrawal(User user);

	boolean deposit(User user);

	void viewBalance(User user);

	List<Transactions> getTransactions(User user);

	void viewAllTransactions(User user);

	boolean userLoggedIn(User user);

	boolean userLogout(User user);

	List<User> getUsers();

}