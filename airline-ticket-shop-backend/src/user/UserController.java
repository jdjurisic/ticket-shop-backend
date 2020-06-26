package user;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UserController {
	private final UserService userService;
	
	public UserController() {
		this.userService = new UserService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserEntity> getUsers(){
		return userService.getUsers();
	}
	
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity register(UserEntity user) {
        return userService.register(user);
    }
	
	
}
