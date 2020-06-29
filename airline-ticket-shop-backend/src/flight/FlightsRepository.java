package flight;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class FlightsRepository {

	public static synchronized List<Flight> getFlights() {

		List<Flight> flg = null;
		Gson gson = new Gson();
		
		Reader reader;
		try {
			reader = new FileReader("flights.json");
			flg = Arrays.asList(gson.fromJson(reader, Flight[].class));
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return flg;
	}

	public static synchronized List<Ticket> getTickets(){
		List<Flight> flg = null;
		Gson gson = new Gson();
		
		Reader reader;
		try {
			reader = new FileReader("flights.json");
			flg = Arrays.asList(gson.fromJson(reader, Flight[].class));
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<Ticket> tickets = new ArrayList<>();
		for(Flight f : flg) {
			if(f.getTickets() != null) {
				for(Ticket t : f.getTickets()) {
					tickets.add(t);
				}
			}

		}
		
		return tickets;
	}
	
	public static synchronized boolean addTicket(Ticket t) {
		
		List<Flight> flg = null;
		Gson gson = new Gson();
		
		Reader reader;
		try {
			reader = new FileReader("flights.json");
			flg = Arrays.asList(gson.fromJson(reader, Flight[].class));
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(t.getReturnDate().before(t.getDepartureDate())) {
			return false;	
		}
		
		for(Flight flig : flg) {
			if(flig.getId() == t.getFlightId()) {
				if(flig.getTickets() == null)flig.setTickets(new ArrayList<Ticket>());
				flig.getTickets().add(t);
				try {
					Writer writer = new FileWriter("flights.json");
					new Gson().toJson(flg,writer);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
		
	}
	
	public static synchronized boolean deleteTicket(Ticket t) {
		
		List<Flight> flg = null;
		Gson gson = new Gson();
		
		Reader reader;
		try {
			reader = new FileReader("flights.json");
			flg = Arrays.asList(gson.fromJson(reader, Flight[].class));
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Flight flig : flg) {
			if(flig.getId() == t.getFlightId()) {
				if(flig.getTickets() == null)return false;
				for(Ticket tck : flig.getTickets()) {
					if(tck.getTicketId() == t.getTicketId()) {
						flig.getTickets().remove(tck);
						try {
							Writer writer = new FileWriter("flights.json");
							new Gson().toJson(flg,writer);
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return true;
					}
				}
			}
		}
		
		
		return false;
	}
	
	//editTicket
	public static synchronized boolean editTicket(Ticket t) {
		
		List<Flight> flg = null;
		Gson gson = new Gson();
		
		Reader reader;
		try {
			reader = new FileReader("flights.json");
			flg = Arrays.asList(gson.fromJson(reader, Flight[].class));
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Flight flig : flg) {
			if(flig.getId() == t.getFlightId()) {
				if(flig.getTickets() == null)return false;
				for(Ticket tck : flig.getTickets()) {
					if(tck.getTicketId() == t.getTicketId()) {
						tck.setCompany(t.getCompany());
						tck.setCount(t.getCount());
						tck.setDepartureDate(t.getDepartureDate());
						tck.setReturnDate(t.getReturnDate());
						tck.setOneway(t.isOneway());
						try {
							Writer writer = new FileWriter("flights.json");
							new Gson().toJson(flg,writer);
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
}
