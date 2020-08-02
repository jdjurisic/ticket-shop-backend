package bookings;

import flight.Flight;
import flight.Ticket;
import user.UserEntity;

public class Booking {
	private int bookingId;
	private boolean isAvailable;
	private int flightId;
	private int ticketId;
	private int userId;
	
	public Booking() {}



	public Booking(int bookingId, boolean isAvailable, int flightId, int ticketId, int userId) {
		super();
		this.bookingId = bookingId;
		this.isAvailable = isAvailable;
		this.flightId = flightId;
		this.ticketId = ticketId;
		this.userId = userId;
	}



	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", isAvailable=" + isAvailable + ", flightId=" + flightId
				+ ", ticketId=" + ticketId + ", userId=" + userId + "]";
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



	public int getFlightId() {
		return flightId;
	}



	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}



	public int getTicketId() {
		return ticketId;
	}



	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	
}
