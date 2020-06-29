package app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class RestApp extends ResourceConfig {

	public RestApp() {
		packages("user",
				"bookings",
				"flight",
				"ticket",
				"company");
	}
}
