package flight;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public Response addTicket(Ticket ticket){
		boolean success = flightsService.addTicket(ticket);
		if(success)return Response.ok().build();
		return Response.notAcceptable(null).build();
	}
	
	@DELETE
	@Path("/tickets")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTicket(Ticket ticket){
		boolean success = flightsService.deleteTicket(ticket);
		if(success)return Response.ok().build();
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
	
}
