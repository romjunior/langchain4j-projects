package com.estudo.queue;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MessageIn {
    private final String sessionId;
    private final String message;

    private MessageIn(String sessionId, String message) {
        this.sessionId = sessionId;
        this.message = message;
    }

    public static MessageIn of(String sessionId, String message) {
        return new MessageIn(sessionId, message);
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "MessageIn{" +
                "memoryId='" + sessionId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
