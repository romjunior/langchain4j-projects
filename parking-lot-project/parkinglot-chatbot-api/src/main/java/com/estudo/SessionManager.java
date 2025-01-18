package com.estudo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SessionManager {

    private final List<Session> sessions;

    public SessionManager() {
        this.sessions = new ArrayList<>();
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public void sendMessage(MessageOut message) {
        sessions.forEach(session -> session.getAsyncRemote().sendObject(message.getAnswer(), sendResult -> {
            if (sendResult.getException() != null) {
                System.out.println("Unable to send message: " + sendResult.getException());
            }
        }));
    }
}
