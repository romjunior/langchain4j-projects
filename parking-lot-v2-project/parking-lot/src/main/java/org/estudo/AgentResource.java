package org.estudo;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.estudo.ai.OrquestratorAgent;

@Path("/chat")
public class AgentResource {

    private final OrquestratorAgent agent;

    public AgentResource(OrquestratorAgent agent) {
        this.agent = agent;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String processMessage(MessageRequest request) {
       return agent.chat(request.memoryId, request.message);
    }

    public static class MessageRequest {
        public String memoryId;
        public String message;
    }
}
