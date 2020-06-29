package company;

import java.security.Key;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import app.JwtKey;
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
	
	
}
