package user;

import java.util.List;

import bookings.Booking;
import flight.Ticket;

public class UserService {
	public List<UserEntity> getUsers(){
		return UserRepository.getUsers();
	}
	
	public UserEntity register(UserEntity user) {
		return UserRepository.addUser(user);
	}
	
	public UserEntity login(UserEntity user) {
		UserEntity user2 = UserRepository.getUserByUsername(user.getUsername());
		if(user2!=null && user.getPassword().equals(user2.getPassword()))return user2;
		return null;
	}

	public boolean reserve(String username, Ticket tick) {
		return UserRepository.reserve(username,tick);
	}
	
}
