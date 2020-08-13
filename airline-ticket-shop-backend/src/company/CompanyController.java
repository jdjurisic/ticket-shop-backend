package company;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import app.JwtKey;
import flight.FlightsRepository;
import flight.Ticket;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import user.UserEntity;
import user.UserRole;
import user.UserService;

@Path("/companies")
public class CompanyController {
	private final CompaniesService companiesService;

	public CompanyController() {
		this.companiesService = new CompaniesService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> getCompanies(@Context HttpServletRequest request){
		String auth = request.getHeader("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(JwtKey.getInstance().getKey()).build().parseClaimsJws(jwt);
			    return companiesService.getCompanies();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response companyTickets(@PathParam("name")String name) {
		List<Ticket> filtered = new ArrayList<Ticket>();
		List<Ticket> tickets = FlightsRepository.getTickets();
		for(Ticket t : tickets) {
			if(t.getCompany().equals(name))filtered.add(t);
		}
		
		if(tickets.size() > 0)return Response.ok().entity(filtered).build();
		else return Response.serverError().build();
	}
	
	@DELETE
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteCompany(@Context HttpServletRequest request,@PathParam("name")String name) {
		String auth = request.getHeader("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(JwtKey.getInstance().getKey()).build().parseClaimsJws(jwt);
			    String role = (String)claims.getBody().get("role");
			    if(role.equals(UserRole.ADMIN.toString())) {
					boolean succ;
					succ = companiesService.deleteCompany(name);
					System.out.println(name);
					if(succ)return true;
			    }
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}
	
	@PUT
	@Path("/{oldName}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editCompany(@Context HttpServletRequest request,@PathParam("oldName")String oldName,String newname) {
		String auth = request.getHeader("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(JwtKey.getInstance().getKey()).build().parseClaimsJws(jwt);
			    String role = (String)claims.getBody().get("role");
			    if(role.equals(UserRole.ADMIN.toString())) {
					boolean succ = false;
					succ = companiesService.editCompany(oldName,newname);
					if(succ)return Response.ok().build();
			    }
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return Response.serverError().build();
	}
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCompany(@Context HttpServletRequest request,String name) {
		String auth = request.getHeader("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(JwtKey.getInstance().getKey()).build().parseClaimsJws(jwt);
			    String role = (String)claims.getBody().get("role");
			    if(role.equals(UserRole.ADMIN.toString())) {
			    	boolean succ = false;
					succ = companiesService.addCompany(name);
					if(succ) Response.ok().build();
			    }
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return Response.serverError().build();
	}
	
}
