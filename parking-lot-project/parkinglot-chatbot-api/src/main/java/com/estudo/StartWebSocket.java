package com.estudo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat/{name}")
@ApplicationScoped
public class StartWebSocket {

    private final ExampleChat exampleChat;

    private Session session;

    public StartWebSocket(ExampleChat exampleChat) {
        this.exampleChat = exampleChat;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name) {
        this.session = session;
        session.getAsyncRemote().sendObject("Hello " + name, sendResult -> {
            if (sendResult.getException() != null) {
                System.out.println("Unable to send message: " + sendResult.getException());
            }
        });
        System.out.println("onOpen> " + name);
    }

    @OnClose
    public void onClose(Session session, @PathParam("name") String name) {
        this.session = null;
        System.out.println("onClose> " + name);
    }

    @OnError
    public void onError(Session session, @PathParam("name") String name, Throwable throwable) {
        this.session = null;
        System.out.println("onError> " + name + ": " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("name") String name) {
        if(session != null)
            session.getAsyncRemote().sendObject(exampleChat.chat(message), sendResult -> {
                if (sendResult.getException() != null) {
                    System.out.println("Unable to send message: " + sendResult.getException());
                }
            });
        System.out.println("onMessage> " + name + ": " + message);
    }
}
