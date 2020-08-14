package user;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import app.JwtKey;
import bookings.Booking;
import flight.Ticket;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Path("/users")
public class UserController {
	private final UserService userService;
	
	public UserController() {
		this.userService = new UserService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserEntity> getUsers(@Context HttpServletRequest request){
		String auth = request.getHeader("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(JwtKey.getInstance().getKey()).build().parseClaimsJws(jwt);
			    String role = (String)claims.getBody().get("role");
			    if(role.equals(UserRole.ADMIN.toString()))return userService.getUsers();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity register(@Context HttpServletRequest request,UserEntity user) {
		String auth = request.getHeader("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(JwtKey.getInstance().getKey()).build().parseClaimsJws(jwt);
			    String role = (String)claims.getBody().get("role");
			    if(role.equals(UserRole.ADMIN.toString()))return userService.register(user);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	    }
        return null;
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserEntity user) {
    	user = userService.login(user);
    	if(user != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("user", user.getUsername());
            claims.put("role", user.getRole().toString());
    		String jws = Jwts.builder().setSubject(user.getUsername()).setClaims(claims).setIssuedAt(new Date()).signWith(JwtKey.getInstance().getKey()).compact();
            return Response.ok().entity(user).header(AUTHORIZATION, "Bearer " + jws).build();
    	} else return Response.status(Response.Status.UNAUTHORIZED).entity(false).build();
    	
    }
    
    @POST
    @Path("/reservations/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reservations(@Context HttpServletRequest request,Ticket ticket,@PathParam("username")String username) {
    	String auth = request.getHeader("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(JwtKey.getInstance().getKey()).build().parseClaimsJws(jwt);
			    String role = (String)claims.getBody().get("role");
			    String usernameFromToken = (String)claims.getBody().get("user");
			    if(role.equals(UserRole.USER.toString()) && usernameFromToken.equals(username) ) {
			    	System.out.println(ticket.toString());
					boolean success = userService.reserve(username,ticket);
					System.out.println(success);
					if(success)return Response.ok().build();
			    }
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
    }
    	return Response.serverError().build();
    }
    
    @GET
    @Path("/reservations/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Booking> reservedTicketsForUser(@Context HttpServletRequest request,@PathParam("username")String username) {
    	String auth = request.getHeader("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(JwtKey.getInstance().getKey()).build().parseClaimsJws(jwt);
			    String role = (String)claims.getBody().get("role");
			    if(role.equals(UserRole.USER.toString())) {
			    	List<Booking> res = userService.getBookings(username);
					if(res.size()>0)return res;
			    }
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
    }
    	return null;
    }
    
    @DELETE
    @Path("/reservations/{username}/{bookingId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReservation(@Context HttpServletRequest request,@PathParam("username")String username,@PathParam("bookingId")int bookingId) {
    	String auth = request.getHeader("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(JwtKey.getInstance().getKey()).build().parseClaimsJws(jwt);
			    String role = (String)claims.getBody().get("role");
			    String usernameFromToken = (String)claims.getBody().get("user");
			    if(role.equals(UserRole.USER.toString()) && usernameFromToken.equals(username)) {
			    	boolean del = false;
			    	del = userService.deleteReservation(username,bookingId);
			    	if(del)return Response.ok().build();
			    }
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
    }
    	return Response.serverError().build();
    }
	
    
}
