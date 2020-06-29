package bookings;

import flight.Flight;
import ticket.Ticket;
import user.UserEntity;

public class Booking {
	private int bookingId;
	private boolean isAvailable;
	private Flight flight;
	private Ticket ticket;
	private UserEntity user;
	
	public Booking() {}

	public Booking(int bookingId, boolean isAvailable, Flight flight, Ticket ticket, UserEntity user) {
		super();
		this.bookingId = bookingId;
		this.isAvailable = isAvailable;
		this.flight = flight;
		this.ticket = ticket;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", isAvailable=" + isAvailable + ", flight=" + flight + ", ticket="
				+ ticket + ", user=" + user + "]";
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	
}
