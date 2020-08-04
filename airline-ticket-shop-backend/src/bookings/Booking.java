package bookings;

import flight.Flight;
import flight.Ticket;
import user.UserEntity;

public class Booking {
	private int bookingId;
	private boolean isAvailable;
	private Ticket ticket;
	
	public Booking() {}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", isAvailable=" + isAvailable + ", ticket=" + ticket + "]";
	}

	public Booking(int bookingId, boolean isAvailable, Ticket ticket) {
		super();
		this.bookingId = bookingId;
		this.isAvailable = isAvailable;
		this.ticket = ticket;
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

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	



	
}
