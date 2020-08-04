package user;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import bookings.Booking;
import flight.FlightsRepository;
import flight.Ticket;

public class UserRepository {

	public static synchronized List<UserEntity> getUsers(){
		
		List<UserEntity> users = null;
		Gson gson = new Gson();
		
		try {
			Reader reader = new FileReader("users.json");
			users = Arrays.asList(gson.fromJson(reader, UserEntity[].class));
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}
	

	public static synchronized UserEntity getUserByUsername(String username) {
		List<UserEntity> users = null;
		Gson gson = new Gson();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("users.json"));
			users = Arrays.asList(gson.fromJson(reader, UserEntity[].class));
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		UserEntity usr = null;
		for(UserEntity ent : users) {
			if(ent.getUsername().equals(username)) {
				usr = ent;
				break;
			}
		}
		
		return usr;
	}
	
	public static synchronized UserEntity addUser(UserEntity user) {
		List<UserEntity> users = null;
		Gson gson = new Gson();
		System.out.println(user.toString());
		try {
			Reader reader = Files.newBufferedReader(Paths.get("users.json"));
			users = Arrays.asList(gson.fromJson(reader, UserEntity[].class));
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(UserEntity entus : users) {
			if(entus.getUsername().equals(user.getUsername())) {
				return null;
			}
		}
		
		List<UserEntity> newUsers = new ArrayList<UserEntity>(users);
		user.setId(users.size()+1);
		if(user.getRole().equals(UserRole.USER)) {
			user.setBookings(new ArrayList<Booking>());
		}
		if((user.getRole().equals(UserRole.ADMIN)||user.getRole().equals(UserRole.USER)) &&  !user.getUsername().equals("")) {
			if(is_Valid_Password(user.getPassword()))newUsers.add(user);
		}

		try {
			Writer writer = new FileWriter("users.json");
			new Gson().toJson(newUsers,writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	// password validation
	public static final int PASSWORD_LENGTH = 6;
	public static boolean is_Valid_Password(String password) {

        if (password.length() < PASSWORD_LENGTH) return false;

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else return false;
        }

        System.out.println(charCount >= 2 && numCount >= 2);
        return (charCount >= 1 && numCount >= 1);
    }

    public static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }


    public static boolean is_Numeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }
    
    // password validation above 
    
	public static boolean reserve(String username, Ticket tick) {
		List<UserEntity> users = null;
		Gson gson = new Gson();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("users.json"));
			users = Arrays.asList(gson.fromJson(reader, UserEntity[].class));
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		UserEntity usr = null;
		for(UserEntity ent : users) {
			if(ent.getUsername().equals(username)) {
				usr = ent;
				break;
			}
		}
	
		if(tick.getCount()-1 >= 0) {
			tick.setCount(tick.getCount()-1);
			FlightsRepository.editTicket(tick);
		}else return false;
		
		usr.getBookings().add(new Booking(usr.getBookings().size()+1,true,tick));
		
		try {
			Writer writer = new FileWriter("users.json");
			new Gson().toJson(users,writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
}
