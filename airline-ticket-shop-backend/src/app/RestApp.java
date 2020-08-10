package app;

import java.security.Key;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@ApplicationPath("/api")
public class RestApp extends ResourceConfig {
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	public RestApp() {
		packages("user",
				"bookings",
				"flight",
				"ticket",
				"company");
		
	}
	
	//TODO companyTickets,deleteCmp,editCmp,AddCmp,allReservations,DeleteReservation
	//DONE getTickets,newTicket,deleteTicket,editTicket,reserveTicket
}
