package com.estudo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    ExampleChat exampleChat;

    public GreetingResource(ExampleChat exampleChat) {
        this.exampleChat = exampleChat;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return exampleChat.chat("Say hello to LangChain4j!");
    }
}
