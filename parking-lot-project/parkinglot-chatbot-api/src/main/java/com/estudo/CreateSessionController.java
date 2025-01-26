package com.estudo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("/create-session")
public class CreateSessionController {

    private final SessionManager sessionManager;

    public CreateSessionController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SessionIdResponse createSession(@QueryParam("name") String name) {
        final var id = UUID.randomUUID();
        sessionManager.createSession(id);
        return new SessionIdResponse(id);
    }

}
