package com.estudo.memory;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;

import java.util.List;

public interface MessagesToStringConverter {

    static String convert(List<ChatMessage> messages) {
        return messages
                .stream()
                .map(MessagesToStringConverter::convertMessagesToString)
                .filter(texts -> !texts.isEmpty())
                .reduce("", (a, b) -> a + "\n" + b);
    }

    static String convert(List<ChatMessage> messages, String lastInput) {
        var text = convert(messages);
        return text + "\n" + "User: " + lastInput;
    }


    private static String convertMessagesToString(ChatMessage message) {
        return switch (message) {
            case UserMessage userMessage -> "User: " + userMessage.singleText();
            case AiMessage aiMessage -> aiMessage.text() == null ? "" : "Assistant: " + aiMessage.text();
            default -> "";
        };
    }
}
