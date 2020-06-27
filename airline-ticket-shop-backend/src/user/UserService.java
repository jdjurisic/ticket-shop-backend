package user;

import java.util.List;

public class UserService {
	public List<UserEntity> getUsers(){
		return UserRepository.getUsers();
	}
	
	public UserEntity register(UserEntity user) {
		return UserRepository.addUser(user);
	}
	
	public UserEntity login(UserEntity user) {
		UserEntity user2 = UserRepository.getUserByUsername(user.getUsername());
		
		if(user2!=null && user.getPassword().equals(user2.getPassword()))return user;
		return null;
	}
	
}
