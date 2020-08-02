package app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import flight.Flight;
import flight.Ticket;

public class Test {

	public static void main(String[] args) {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		
		Flight f1 = new Flight();
		f1.setDestination("Belgrade");
		f1.setOrigin("Basel");
		f1.setId(0);
		ArrayList<Ticket> t1 = new ArrayList<>();
		
		Ticket t11 = new Ticket();
		t11.setCompany("Swiss Air");
		t11.setCount(50);
		t11.setDepartureDate(new Date(2020,5,1,8,0,0));
		t11.setReturnDate(new Date(2020,5,25,8,0,0));
		t11.setFlightId(0);
		t11.setOneway(false);
		t11.setTicketId(0);
		t1.add(t11);
		
		Ticket t12 = new Ticket();
		t12.setCompany("Swiss Air");
		t12.setCount(50);
		t12.setDepartureDate(new Date(2020,7,1,9,0,0));
		t12.setReturnDate(new Date(2020,7,25,9,0,0));
		t12.setFlightId(0);
		t12.setOneway(false);
		t12.setTicketId(0);
		t1.add(t12);
		
		f1.setTickets(t1);
		flights.add(f1);
		
		// another 
		Flight f2 = new Flight();
		f2.setDestination("Tungusia");
		f2.setOrigin("Paris");
		f2.setId(0);
		ArrayList<Ticket> t2 = new ArrayList<>();
		
		Ticket t21 = new Ticket();
		t21.setCompany("Swiss Air");
		t21.setCount(50);
		t21.setDepartureDate(new Date(2020,5,1,8,0,0));
		t21.setReturnDate(new Date(2020,5,25,8,0,0));
		t21.setFlightId(0);
		t21.setOneway(false);
		t21.setTicketId(0);
		t2.add(t21);
		
		Ticket t22 = new Ticket();
		t22.setCompany("Swiss Air");
		t22.setCount(50);
		t22.setDepartureDate(new Date(2020,7,1,9,0,0));
		t22.setReturnDate(new Date(2020,7,25,9,0,0));
		t22.setFlightId(0);
		t22.setOneway(false);
		t22.setTicketId(0);
		t2.add(t22);
		
		f2.setTickets(t2);
		flights.add(f2);
		
		try {
			Writer writer = new FileWriter("testflights.json");
			new Gson().toJson(flights,writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
