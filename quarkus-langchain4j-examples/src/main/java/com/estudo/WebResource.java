package com.estudo;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("ia")
public class WebResource {

    private final MyIAService myIAService;

    private final SentimentAnalysis sentimentAnalysis;

    private final ChatMemory chatMemory;

    public WebResource(MyIAService myIAService,
                       SentimentAnalysis sentimentAnalysis,
                       ChatMemory chatMemory) {
        this.myIAService = myIAService;
        this.sentimentAnalysis = sentimentAnalysis;
        this.chatMemory = chatMemory;
    }

    @POST
    @Path("/chat")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String chat(String body) {
        return myIAService.chat(body);
    }

    @POST
    @Path("/sentiment-analysis")
    public String sentimentAnalysis(String body) {
        return sentimentAnalysis.analyzeSentiment(body).name();
    }

    @POST
    @Path("/chat-memory/{id}")
    public String chatMemory(@PathParam("id") String id, String body) {
        return chatMemory.chat(id, body);
    }
}
