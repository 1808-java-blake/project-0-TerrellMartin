package com.revature.daos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.account.Transactions;
import com.revature.beans.User;

public class PyramidSerializer implements PyramidDao {
	private final String FILE_LOCATION = "src/main/resources/bank.txt";
	private Scanner scan = new Scanner(System.in);
	private List<User> users = getUsers();
	private List<Transactions> transaction;
	private List<Transactions> transactions;
	private double amount;


	@Override
	public boolean witdrawal(User user) {

		System.out.println("How much would you like to withdraw " + user.getFirstName() + " ?");
		amount = scan.nextDouble();

		for (User u : users) {
			if (u.getPyramidAccount().getAccountNumber() == user.getPyramidAccount().getAccountNumber()) {
				if (u.getPyramidAccount().getAccountBalance() >= amount) {
					transaction = getTransactions(user);
					transaction.add(new Transactions("withdrawl", amount, LocalDateTime.now()));
					u.getPyramidAccount().setAccountBalance(u.getPyramidAccount().getAccountBalance() - amount);
					u.getPyramidAccount().setTransactions(transaction);
				} else {
					System.out.println("You do not have enough funds for the requested amount.");
					return false;
				}
			}
		}
		
		return outputToFile(users);
	}

	@Override
	public boolean deposit(User user) {
		System.out.print("How much would you like to deposit " + user.getFirstName() + " ?");
		amount = scan.nextDouble();

		for (User u : users) {
			if (u.getPyramidAccount().getAccountNumber() == user.getPyramidAccount().getAccountNumber()) {
				transaction = getTransactions(user);
				transaction.add(new Transactions("deposit", amount, LocalDateTime.now()));
				u.getPyramidAccount().setAccountBalance(u.getPyramidAccount().getAccountBalance() + amount);
				u.getPyramidAccount().setTransactions(transaction);
			}
		}
		
		return outputToFile(users);
	}

	@Override
	public void viewBalance(User user) {
		// TODO Auto-generated method stub
		for (User u : users) {
			if (u.getPyramidAccount().getAccountNumber() == user.getPyramidAccount().getAccountNumber()) {
				System.out.printf("\nAccount balance: $%,.2f\n", u.getPyramidAccount().getAccountBalance());
			}
		}
	}

	@Override
	public List<Transactions> getTransactions(User user) {
		// TODO Auto-generated method stub
		for (User u : users) {
			if (u.getPyramidAccount().getAccountNumber() == user.getPyramidAccount().getAccountNumber()) {
				transactions = u.getPyramidAccount().getTransactions();
			}
		}

		return transactions;
	}

	// The login method. to keep track of who is logged on.
	@Override
	public boolean userLoggedIn(User user) {
		for (User u : users) {
			if (u.getPyramidAccount().getAccountNumber() == user.getPyramidAccount().getAccountNumber())
				u.setLoggedIn(true);
		}
		
		return outputToFile(users);

	}


	@Override
	public boolean addUser(User user) {
		if (users == null) {
			users = new ArrayList<>();
		}

		for (User u : users) {
			if (u.getPyramidAccount().getAccountNumber() == user.getPyramidAccount().getAccountNumber()) {
				return false;
			} else if (u.getUsername().equals(user.getUsername())) {
				System.out.println("This username is taken, please try again.");
				return false;
			}
		}
		
		users.add(user);

		return outputToFile(users);

	}

	@Override
	public List<User> getUsers() {
		try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(FILE_LOCATION))) {
			return (List<User>) inStream.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	// The login method. to keep track of who is logged on.
	@Override
	public boolean userLogout(User user) {
		// TODO Auto-generated method stub
		for (User u : users) {
			if (u.getPyramidAccount().getAccountNumber() == user.getPyramidAccount().getAccountNumber())
				u.setLoggedIn(false);
		}
		
		return outputToFile(users);
	}

	// only the admin can do this method.
	@Override
	public void viewAllTransactions(User user) {
		// TODO Auto-generated method stub
		if (users.size() > 1) {
			for (User u : users) {
				if (u.getPyramidAccount().getAccountNumber() != 0) {
					transactions = u.getPyramidAccount().getTransactions();
					System.out.printf("User: %s", u.getUsername());
					for (Transactions transaction : transactions) {
						System.out.printf(
								"\nA %s for the amount of $%,.2f was made on %tB %<te, %<tY at %<tH:%<tM %<Tp",
								transaction.getType(), transaction.getAmount(), transaction.getDate());

					}
				}
				System.out.println("\n");
			}
		} else
			System.out.println("No transactions currently have been made at this time.");

	}

	public boolean outputToFile(List<User> users) {
		try (ObjectOutputStream oStream = new ObjectOutputStream(new FileOutputStream(FILE_LOCATION))) {
			oStream.writeObject(users);
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return false;
	}

}