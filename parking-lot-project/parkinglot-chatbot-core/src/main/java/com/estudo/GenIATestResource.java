package com.estudo;

import com.estudo.llm.Assistant;
import io.quarkiverse.langchain4j.runtime.aiservice.GuardrailException;
import jakarta.ws.rs.Consumes;
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
        try {
            return assistant.chat(memoryId, chatBody.message());
        } catch (GuardrailException e) {
            if(e.getMessage().contains("CheckContentFailed")) {
                return "Me desculpe mas eu não posso falar sobre qualquer outro assunto além da gestão do estacionamento que eu faço, o que você precisa?";
            } else {
                throw e;
            }
        }
    }

    record ChatBody(String message) {}
}
