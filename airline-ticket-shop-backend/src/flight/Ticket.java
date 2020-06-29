package flight;

import java.util.Date;

public class Ticket {
	private int ticketId;
	private String company;
	private boolean oneway;
	private Date departureDate;
	private Date returnDate;
	private int flightId;
	private int count;
	
	public Ticket() {}



	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", company=" + company + ", oneway=" + oneway + ", departureDate="
				+ departureDate + ", returnDate=" + returnDate + ", flightId=" + flightId + ", count=" + count + "]";
	}



	public Ticket(int ticketId, String company, boolean oneway, Date departureDate, Date returnDate, int flightId,
			int count) {
		super();
		this.ticketId = ticketId;
		this.company = company;
		this.oneway = oneway;
		this.departureDate = departureDate;
		this.returnDate = returnDate;
		this.flightId = flightId;
		this.count = count;
	}



	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public boolean isOneway() {
		return oneway;
	}

	public void setOneway(boolean oneway) {
		this.oneway = oneway;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	
}
