package com.revature.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.revature.beans.User;

public class UserSerializer implements UserDao {

	

	@Override
	public void createUser(User u) {
		if (u == null) {
			return;
		}
		File f = new File("src/main/resources/users/" + u.getUsername() + ".txt");
		System.out.println(f.getName());
		if (f.exists()) {
			return;
		}
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream("src/main/resources/users/" + u.getUsername() + ".txt"))) {

			oos.writeObject(u);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		// verify that what was passed in is not null
		if (username == null || password == null) {
			return null;
		}
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("src/main/resources/users/" + username + ".txt"))) {

			User u = (User) ois.readObject(); // retrieve the user if it can
			// verify that the password matches
			if (password.equals(u.getPassword()) && username.equals(u.getUsername())) {
				return u;
			} else {
				System.out.println("We found the user credentials, but you did not enter the correct password.");
				return null;
			}

		} catch (FileNotFoundException e) {
			System.out.println("This user does not exist.");
			e.printStackTrace();
		} catch (IOException e) {	
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateUser(User u) {
		//incase they try to put null or nothing just return.
		if (u == null){
			return;
		}
		File updateU = new File("src/main/resources/users/" + u.getUsername() + ".txt");
		if (updateU.exists()){
			try (ObjectOutputStream serial = new ObjectOutputStream(new FileOutputStream(updateU))) 
			{
				serial.writeObject(u);
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			
		}
		else {
			System.out.println("The user is not found.");
		}

	}

	@Override
	public void deleteUser(User u) {
		if ( u == null){
			return;
		}
		File deleteU = new File("src/main/resources/users/" + u.getUsername() + ".txt");
		if (deleteU.exists()){
			deleteU.delete();
		}
		else {
			System.out.println("There are no records of this user. ");
		}
	}

	

}
