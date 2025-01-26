package com.estudo.queue;

public class MessageOut {
    private String sessionId;
    private String answer;

    public MessageOut(String sessionId, String answer) {
        this.sessionId = sessionId;
        this.answer = answer;
    }

    public static MessageOut of(String sessionId, String answer) {
        return new MessageOut(sessionId, answer);
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getAnswer() {
        return answer;
    }
}
