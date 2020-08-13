package company;

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
import flight.Flight;
import flight.FlightsRepository;
import flight.Ticket;
import user.UserEntity;
import user.UserRepository;
import user.UserRole;

public class CompaniesRepository {
	public static synchronized List<Company> getCompanies(){
		
		List<Company> cmp = null;
		Gson gson = new Gson();
		
		try {
			Reader reader = new FileReader("companies.json");
			cmp = Arrays.asList(gson.fromJson(reader, Company[].class));
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cmp;
		
	}

	public static synchronized boolean deleteCompany(String name) {
		
		List<Company> cmp = null;
		Gson gson = new Gson();
		boolean suc = false;
		
		try {
			Reader reader = new FileReader("companies.json");
			cmp = Arrays.asList(gson.fromJson(reader, Company[].class));
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Company> cmpns = new ArrayList<Company>(cmp);
		List<Company> toDelete = new ArrayList<Company>();
		for(Company c : cmpns) {
			if(c.getName().equals(name)) {
				toDelete.add(c);
				suc = true;
			}
		}
		
		for(Company komp:toDelete) {
			cmpns.remove(komp);
		}
		
		try {
			Writer writer = new FileWriter("companies.json");
			new Gson().toJson(cmpns,writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		List<Ticket> tickets = FlightsRepository.getTickets();
		List<Ticket> splice = new ArrayList<Ticket>(tickets);
		for(Ticket t:splice) {
			if(t.getCompany().equals(name)) {
				FlightsRepository.deleteTicket(t);
			}
		}
		 //clear reservations
//		List<UserEntity> users =  UserRepository.getUsers();
//		List<UserEntity> usr2 = new ArrayList<UserEntity>(users);
//		
//		for(UserEntity usr : usr2) {
//			if(usr.getRole().equals(UserRole.USER)) {
//
//					for(Booking booking : usr.getBookings()) {
//						if(booking.getTicket().getCompany().equals(name)) {
//							usr.getBookings().remove(booking);
//							try {
//								Writer writer1 = new FileWriter("users.json");
//								new Gson().toJson(usr2,writer1);
//								writer1.close();
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//			}
//		}
//
//		// clear tickets
//		List<Flight> flg1 = FlightsRepository.getFlights();
//		List<Flight> flg = new ArrayList<Flight>(flg1);
//		
//		for(Flight flig : flg) {
//			for(Ticket t:flig.getTickets()) {
//				if(t.getCompany().equals(name)) {
//					flig.getTickets().remove(t);
//					try {
//						Writer writer2 = new FileWriter("flights.json");
//						new Gson().toJson(flg,writer2);
//						writer2.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
		//}
			

		
		return suc;
	}

	public static boolean editCompany(String oldName, String newname) {
		List<Company> cmp = null;
		Gson gson = new Gson();
		
		try {
			Reader reader = new FileReader("companies.json");
			cmp = Arrays.asList(gson.fromJson(reader, Company[].class));
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boolean done = false;
		List<Company> newCmp = new ArrayList<Company>(cmp);
		for(Company c:newCmp) {
			if(c.getName().equals(oldName)) {
				c.setName(newname);
				done = true;
			}
		}
		
		try {
			Writer writer = new FileWriter("companies.json");
			new Gson().toJson(newCmp,writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//TODO change tickets and reservations
		List<Ticket> tickets = FlightsRepository.getTickets();
		List<Ticket> splice = new ArrayList<Ticket>(tickets);
		for(Ticket t:splice) {
			if(t.getCompany().equals(oldName)) {
				t.setCompany(newname);
				FlightsRepository.editTicket(t);
			}
		}
		
		return done;
	}
	
	
}

