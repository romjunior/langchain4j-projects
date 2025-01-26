package com.estudo;

import io.quarkus.logging.Log;
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
    private final Emitter<JsonObject> messageEmitter;
    private final SessionManager sessionManager;


    public StartWebSocket(@Channel("messages-in") Emitter<JsonObject> messageEmitter,
                          SessionManager sessionManager) {
        this.messageEmitter = messageEmitter;
        this.sessionManager = sessionManager;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name) {
        sessionManager.addSession(session);
        Log.info("onOpen=" + name);
    }

    @OnClose
    public void onClose(Session session, @PathParam("name") String name) {
        sessionManager.removeSession(session);
        Log.info("onClose=" + name);
    }

    @OnError
    public void onError(Session session, @PathParam("name") String name, Throwable throwable) {
        sessionManager.removeSession(session);
        Log.info("onError=" + name + " error=" + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("name") String name) {
        messageEmitter.send(new JsonObject().put("memoryId", name)
                .put("message", message));
        Log.info("onMessage=" + name + " message=" + message);
    }
}
