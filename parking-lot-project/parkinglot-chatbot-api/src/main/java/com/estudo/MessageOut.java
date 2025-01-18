package com.estudo;

public class MessageOut {
    private String answer;

    public MessageOut(String answer) {
        this.answer = answer;
    }

    public static MessageOut of(String answer) {
        return new MessageOut(answer);
    }

    public String getAnswer() {
        return answer;
    }
}
