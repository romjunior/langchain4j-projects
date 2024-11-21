package com.estudo.memory;


import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@RequestScoped
public class ParkinglotChatMemoryProvider implements ChatMemoryProvider {

    private final ParkinglotChatMemoryStore parkinglotChatMemoryStore;

    private final Integer messagesQuantity;

    public ParkinglotChatMemoryProvider(ParkinglotChatMemoryStore parkinglotChatMemoryStore,
                                        @ConfigProperty(name = "parkinglot.messagesQuantity") Integer messagesQuantity) {
        this.parkinglotChatMemoryStore = parkinglotChatMemoryStore;
        this.messagesQuantity = messagesQuantity;
    }

    @Override
    public ChatMemory get(Object memoryId) {
        return MessageWindowChatMemory.builder()
                .maxMessages(messagesQuantity)
                .id(memoryId)
                .chatMemoryStore(parkinglotChatMemoryStore)
                .build();
    }
}
