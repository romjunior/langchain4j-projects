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

import java.util.UUID;

@ServerEndpoint("/chat/{id}")
@ApplicationScoped
public class WebSocketManager {
    private final Emitter<JsonObject> messageEmitter;
    private final SessionManager sessionManager;


    public WebSocketManager(@Channel("messages-in") Emitter<JsonObject> messageEmitter,
                            SessionManager sessionManager) {
        this.messageEmitter = messageEmitter;
        this.sessionManager = sessionManager;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        sessionManager.addSession(id, session);
        Log.info("onOpen=" + id);
    }

    @OnClose
    public void onClose(Session session, @PathParam("id") String id) {
        sessionManager.removeSession(id);
        Log.info("onClose=" + id);
    }

    @OnError
    public void onError(Session session, @PathParam("id") String id, Throwable throwable) {
        sessionManager.removeSession(id);
        Log.info("onError=" + id + " error=" + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("id") String id) {
        messageEmitter.send(new JsonObject().put("sessionId", id)
                .put("message", message));
        Log.info("receiveMessage=" + id + " message=" + message);
    }
}
