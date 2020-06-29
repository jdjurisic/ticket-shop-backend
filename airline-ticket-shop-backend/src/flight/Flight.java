package flight;

import java.util.ArrayList;

public class Flight {
	private int id;
	private ArrayList<Ticket> tickets;
	private String origin;
	private String destination;
	
	public Flight() {}

	public Flight(int id, ArrayList<Ticket> tickets, String origin, String destination) {
		super();
		this.id = id;
		this.tickets = tickets;
		this.origin = origin;
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "Flight [id=" + id + ", tickets=" + tickets + ", origin=" + origin + ", destination=" + destination
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
}
