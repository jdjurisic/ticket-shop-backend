package flight;

import java.util.List;

public class FlightsService {
	public List<Flight> getFlights(){
		return FlightsRepository.getFlights();
	}
	
	public List<Ticket> getTickets(){
		return FlightsRepository.getTickets();
	}
	
	public boolean addTicket(Ticket t) {
		return FlightsRepository.addTicket(t);
	}
	
	public boolean deleteTicket(Ticket t) {
		return FlightsRepository.deleteTicket(t);
	}
	
	public boolean editTicket(Ticket t) {
		return FlightsRepository.editTicket(t);
	}

	public Ticket getTicketById(int fid,int ticketId) {
		return FlightsRepository.getTicketById(fid,ticketId);
	}
	
}
