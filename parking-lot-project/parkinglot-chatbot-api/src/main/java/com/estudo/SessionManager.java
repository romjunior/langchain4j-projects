package com.estudo;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class SessionManager {

    private final Map<UUID, Session> sessions;

    public SessionManager() {
        this.sessions = new HashMap<>();
    }

    public void createSession(UUID id) {
        sessions.put(id, null);
    }

    public void addSession(String id, Session session) {
        final var uuid = UUID.fromString(id);
        if(sessions.containsKey(uuid)) {
            sessions.put(uuid, session);
        } else {
            throw new RuntimeException("Session not found");
        }
    }

    public void removeSession(String id) {
        final var uuid = UUID.fromString(id);
        sessions.remove(uuid);
    }

    public void sendMessage(MessageOut message) {
        sessions.get(UUID.fromString(message.getSessionId())).getAsyncRemote().sendObject(message.getAnswer(), sendResult -> {
            if (sendResult.getException() != null) {
                Log.error("Unable to send message=" + sendResult.getException());
            }
            Log.info("sendMessage=" + message.getSessionId() + " message=" + message.getAnswer());
        });
    }
}
