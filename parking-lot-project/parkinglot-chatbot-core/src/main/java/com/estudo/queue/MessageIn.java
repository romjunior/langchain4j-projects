package com.estudo.queue;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MessageIn {
    private final String memoryId;
    private final String message;

    private MessageIn(String memoryId, String message) {
        this.memoryId = memoryId;
        this.message = message;
    }

    public static MessageIn of(String memoryId, String message) {
        return new MessageIn(memoryId, message);
    }

    public String getMemoryId() {
        return memoryId;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "MessageIn{" +
                "memoryId='" + memoryId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
