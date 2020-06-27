package user;

import java.util.ArrayList;

import bookings.Booking;

public class UserEntity {
	
	private int id;
	private String username;
	private String password;
	private UserRole role;
	private ArrayList<Booking> bookings;
	
    public UserEntity() {}
    
	public UserEntity(int id, String username, String password, UserRole role, ArrayList<Booking> bookings) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.bookings = bookings;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public ArrayList<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(ArrayList<Booking> bookings) {
		this.bookings = bookings;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", bookings=" + bookings + "]";
	}
    
    
	
}
