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
		
//		if(users == null) {
//			users = new ArrayList<UserEntity>();
//		}

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
		
		List<UserEntity> newUsers = new ArrayList<UserEntity>(users);
		newUsers.add(user);

		try {
			Writer writer = new FileWriter("users.json");
			new Gson().toJson(newUsers,writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	
}
