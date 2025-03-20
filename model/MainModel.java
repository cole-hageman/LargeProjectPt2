package model;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MainModel {

	private HashMap<String, LibraryModel> userDatabase; // database of usernames and users
	
	private boolean loggedIn;
	
	private LibraryModel user;
	
	public MainModel() {
		
		/*
		 * TODO: Read from usernames and passwords file and store into hashtable
		 * as well as populate users
		 */
		userDatabase = new HashMap<String, LibraryModel>();
		
		loggedIn = false;
	}
	
	public static String hashPassword(String password, String salt) {
		
		try {
			
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPassword = salt + password; // Combine salt with password
            byte[] hashedBytes = md.digest(saltedPassword.getBytes());

            return Base64.getEncoder().encodeToString(hashedBytes); // Convert to string
            
        } catch (NoSuchAlgorithmException e) {
        	
            throw new RuntimeException("SHA-256 Algorithm not found", e);
            
        }
	}
	
	public boolean loginAttempt(String username, String password) {
		
		if (userDatabase.containsKey(username) && userDatabase.get(username).checkPassword(password)) {
			
			user = userDatabase.get(username);
			loggedIn = true;
			return true;
			
		} else {
			return false;
		}
		
	}
	
	public boolean createAccount(String username, String password) {
		
		if (!userDatabase.containsKey(username)) {
			
			LibraryModel newUser = new LibraryModel(password);
			userDatabase.put(username, newUser);
			
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean logout() {
		
		if (loggedIn) {
			loggedIn = false;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	public LibraryModel getUserLibrary() {
		return user;
	}
	
	public boolean saveDatabase() {
		
		/*
		 * TODO: write usernames and passwords to file
		 */
		
		return false;
	}
}
