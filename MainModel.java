package model;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MainModel {

	private HashMap<String, LibraryModel> userDatabase; // database of usernames and users
	
	private boolean loggedIn;
	
	private LibraryModel user;
	
	public MainModel() {
		
		userDatabase = new HashMap<String, LibraryModel>();
		
		loggedIn = false;
		
        try (Scanner scanner = new Scanner(new File("src/data.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                LibraryModel user = new LibraryModel(parts[1].trim());
                if (parts.length == 2) {
                    userDatabase.put(parts[0].trim(), user);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("data.txt not found");
        }
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
	
	/*
	 * INTENTIONAL ESCAPING REFERENCE, Discussed with Lotz and she says its fine.
	 */
	public LibraryModel getUserLibrary() {
		return user;
	}
	
	public boolean saveDatabase() {
		
        try (PrintWriter writer = new PrintWriter(new File("src/data.txt"))) {
            for (var user : userDatabase.entrySet()) {
                writer.println(user.getKey() + "," + user.getValue().getHashedPw());
            }
        } catch (FileNotFoundException e) {
            System.out.println("data.txt writing to file");
        }
		
		return false;
	}
}
