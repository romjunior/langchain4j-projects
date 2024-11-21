package com.estudo;

import com.estudo.llm.Assistant;
import com.estudo.memory.ParkingLotMemoryManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/genia")
public class GenIATestResource {

    private final Assistant assistant;

    public GenIATestResource(Assistant assistant) {
        this.assistant = assistant;
    }

    @POST
    @Path("/{memoryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String chat(@PathParam("memoryId") String memoryId, ChatBody chatBody) {
        return assistant.chat(memoryId, chatBody.message());
    }

    record ChatBody(String message) {}
}
