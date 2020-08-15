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

import bookings.Booking;
import user.UserEntity;
import user.UserRepository;
import user.UserRole;

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
					if(t.getCount() != 0) {
						t.setDepCity(f.getOrigin());
						t.setDestCity(f.getDestination());
						tickets.add(t);
					}

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
				t.setTicketId(flig.getTickets().size()+1);
				t.setDestCity(flig.getDestination());
				t.setDepCity(flig.getOrigin());
				if(t.isOneway())t.setReturnDate(null);
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
						
						// clear reservations
						List<UserEntity> users =  UserRepository.getUsers();
						List<UserEntity> usr2 = new ArrayList<UserEntity>(users);
						
						for(UserEntity usr : usr2) {
							if(usr.getRole().equals(UserRole.USER)) {

									for(Booking booking : usr.getBookings()) {
										if(booking.getTicket().getTicketId() == t.getTicketId() && 
												booking.getTicket().getFlightId() == t.getFlightId()){
											usr.getBookings().remove(booking);
											try {
												Writer writer = new FileWriter("users.json");
												new Gson().toJson(usr2,writer);
												writer.close();
											} catch (IOException e) {
												e.printStackTrace();
											}
												}
									}

							}
						}
						// reservations
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
						if(t.isOneway())tck.setReturnDate(null);
						try {
							Writer writer = new FileWriter("flights.json");
							new Gson().toJson(flg,writer);
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				// change reservations
						List<UserEntity> users =  UserRepository.getUsers();
						for(UserEntity usr : users) {
							if(usr.getRole().equals(UserRole.USER)) {
								if(usr.getBookings() != null) {
									for(Booking booking : usr.getBookings()) {
										if(booking.getTicket().getTicketId() == t.getTicketId() && 
												booking.getTicket().getFlightId() == t.getFlightId()){
													booking.setTicket(t);
												}
									}
								}
							}
						}
						try {
							Writer writer = new FileWriter("users.json");
							new Gson().toJson(users,writer);
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

	public static synchronized Ticket getTicketById(int fid,int ticketId) {
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
		
		Ticket t = null;
		for(Flight f : flg) {
			if(f.getTickets() != null) {
				if(f.getId() == fid) {
					for(Ticket tick : f.getTickets()) {
						if(tick.getTicketId() == ticketId) {
							t = tick;
						}
					}
				}
			}
		}
		
		return t;
	}

	public static List<Ticket> getTicketPage(int page) {
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
					if(t.getCount() != 0) {
						t.setDepCity(f.getOrigin());
						t.setDestCity(f.getDestination());
						tickets.add(t);
					}

				}
			}

		}
		
		int start = page*5;
		if(start > tickets.size()-1)start = tickets.size()-6;
		int end = start+5 > tickets.size()-1 ? tickets.size():start+5;		
//		System.out.println(start+"-"+end);
//		System.out.println(tickets.size());
		List<Ticket> ticketsForPage = tickets.subList(start, end);
		
		
		return ticketsForPage;
	}

}
