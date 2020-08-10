package flight;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import app.JwtKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import user.UserRole;

@Path("/flights")
public class FlightController {

	private final FlightsService flightsService;
	
	public FlightController() {
		this.flightsService = new FlightsService();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Flight> getFlights(){
		return flightsService.getFlights();
	}
	
	@GET
	@Path("/tickets")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ticket> getTickets(){
		return flightsService.getTickets();
	}
	
	@POST
	@Path("/tickets")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTicket(@Context HttpServletRequest request,Ticket ticket){
		String auth = request.getHeader("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(JwtKey.getInstance().getKey()).build().parseClaimsJws(jwt);
			    String role = (String)claims.getBody().get("role");
			    if(role.equals(UserRole.ADMIN.toString())) {
			    	boolean success = flightsService.addTicket(ticket);
					if(success)return Response.ok().build();
			    }
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return Response.notAcceptable(null).build();
	}
	
	@DELETE
	@Path("/tickets")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTicket(@Context HttpServletRequest request,Ticket ticket){
		String auth = request.getHeader("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(JwtKey.getInstance().getKey()).build().parseClaimsJws(jwt);
			    String role = (String)claims.getBody().get("role");
			    if(role.equals(UserRole.ADMIN.toString())) {
					boolean success = flightsService.deleteTicket(ticket);
					if(success)return Response.ok().build();
			    }			} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			    return Response.notAcceptable(null).build();
	}
	
	@PUT
	@Path("/tickets")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editTicket(Ticket ticket){
		boolean success = flightsService.editTicket(ticket);
		if(success)return Response.ok().build();
		return Response.notAcceptable(null).build();
	}
	
	@GET
	@Path("/tickets/{id}/{ticketId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Ticket getTicketById(@PathParam("id")int fid, @PathParam("ticketId")int ticketId) {
		return flightsService.getTicketById(fid,ticketId);
	}
	
	
	
}
