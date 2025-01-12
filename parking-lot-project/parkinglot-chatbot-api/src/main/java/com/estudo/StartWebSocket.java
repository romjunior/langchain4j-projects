package com.estudo;

import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ServerEndpoint("/chat/{name}")
@ApplicationScoped
public class StartWebSocket {
    private Emitter<JsonObject> messageEmitter;

    private Session session;

    public StartWebSocket(@Channel("messages-in") Emitter<JsonObject> messageEmitter) {
        this.messageEmitter = messageEmitter;
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
        messageEmitter.send(new JsonObject().put("memoryId", name)
                .put("message", message));
        if(session != null)
            session.getAsyncRemote().sendObject("teste", sendResult -> {
                if (sendResult.getException() != null) {
                    System.out.println("Unable to send message: " + sendResult.getException());
                }
            });
        System.out.println("onMessage> " + name + ": " + message);
    }
}
