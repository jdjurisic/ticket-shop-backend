package flight;

import java.sql.Date;
import java.util.ArrayList;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import app.JwtKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import pagination.PageInfo;
import pagination.PaginationResponse;
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
	
	@GET
	@Path("/query")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ticket> searchTickets(@QueryParam("destination")String destination,
			@QueryParam("origin")String origin,@QueryParam("depDate")long dep,@QueryParam("retDate")long ret){
		
		List<Ticket> tickets = flightsService.getTickets();
		List<Ticket> filtered = new ArrayList<Ticket>();
		
		Date departureDate = new Date(dep);
		Date returnDate = new Date(ret);
		System.out.println(origin+"-"+destination);
		System.out.println(departureDate+" --- "+returnDate);
		
		if(destination != null && origin != null) {
			for(Ticket t : tickets) {
				if(t.getDestCity().equals(destination) && t.getDepCity().equals(origin)) {
					filtered.add(t);
				}
			}
		}else {
			if(destination != null) {
				for(Ticket t : tickets) {
				if(t.getDestCity().equals(destination))filtered.add(t);
										}
									}
			if(origin != null) {
				for(Ticket t : tickets) {
					if(t.getDepCity().equals(origin))filtered.add(t);
				}
			}
		}
		
		if(dep == 0 && ret == 0)return filtered;
		if(destination == null && origin == null)filtered = new ArrayList<Ticket>(tickets);

		
		if(dep > 0 && ret > 0) {
			for(Ticket t : new ArrayList<Ticket>(filtered)) {
				if(t.isOneway()) {
					if(departureDate.after(t.getDepartureDate()))filtered.remove(t);
				}else if(!t.isOneway()) {
					if(t.getDepartureDate().before(departureDate) || t.getReturnDate().after(returnDate))filtered.remove(t);
				}
			}
			return filtered;
		}else if(dep > 0) {
			for(Ticket t : new ArrayList<Ticket>(filtered)) {
				if(t.getDepartureDate().before(departureDate))filtered.remove(t);
			}
		}else if(ret > 0) {
			for(Ticket t : new ArrayList<Ticket>(filtered)) {
				if(t.isOneway())continue;
				if(t.getReturnDate().after(returnDate))filtered.remove(t);
			}
		}
		return filtered;

	}
	
	@GET
	@Path("/ticketpage")
	@Produces(MediaType.APPLICATION_JSON)
	public PaginationResponse getTickets(@QueryParam("page")int page) {
		PageInfo pageInfo = new PageInfo(page,5);
		List<Ticket> tickets = flightsService.getTicketPage(page);
		return new PaginationResponse(pageInfo,tickets);
	}
	
	@GET
	@Path("/maxticketpage")
	@Produces(MediaType.APPLICATION_JSON)
	public int maxTicketPage() {
		List<Ticket> tickets = flightsService.getTickets();
		return tickets.size()/5;
	}
	
	
}
