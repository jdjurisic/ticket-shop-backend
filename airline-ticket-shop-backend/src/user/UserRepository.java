package user;

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
		
		if(users == null) {
			users = new ArrayList<UserEntity>();
			users.add(new UserEntity(23,"perica","peric",UserRole.ADMIN,null));
		}

		return users;
	}
	

	public static synchronized UserEntity getUserByUsername(String username) {
		return null;
	}
	
	public static synchronized UserEntity addUser(UserEntity user) {
		List<UserEntity> users = null;
		Gson gson = new Gson();
		
		try {
			Reader reader = Files.newBufferedReader(Paths.get("users.json"));
			users = Arrays.asList(gson.fromJson(reader, UserEntity[].class));
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(users == null)users = new ArrayList<UserEntity>();
		users.add(user);
		
		try {
			Writer writer = new FileWriter("users.json");
			new Gson().toJson(users,writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	
}
