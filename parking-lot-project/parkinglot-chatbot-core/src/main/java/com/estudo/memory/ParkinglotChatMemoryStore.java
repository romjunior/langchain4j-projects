package com.estudo.memory;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;


@RequestScoped
public class ParkinglotChatMemoryStore implements ChatMemoryStore {


    public static final String MEMORY_ID_MUST_BE_A_STRING = "memoryId must be a String";
    private final ParkingLotMemoryManager parkingLotMemoryManager;

    public ParkinglotChatMemoryStore(ParkingLotMemoryManager parkingLotMemoryManager) {
        this.parkingLotMemoryManager = parkingLotMemoryManager;
    }

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        if(memoryId instanceof String sMemoryId) {
            final var result = parkingLotMemoryManager.get(sMemoryId);
            if(result != null) {
                return messagesFromJson(result);
            }
        } else {
            throw new IllegalArgumentException(MEMORY_ID_MUST_BE_A_STRING);
        }
        return List.of();
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        if(memoryId instanceof String sMemoryId) {
            parkingLotMemoryManager.set(sMemoryId, messagesToJson(list));
        } else {
            throw new IllegalArgumentException(MEMORY_ID_MUST_BE_A_STRING);
        }
    }

    @Override
    public void deleteMessages(Object memoryId) {
        if (memoryId instanceof String sMemoryId) {
            parkingLotMemoryManager.delete(sMemoryId);
        } else {
            throw new IllegalArgumentException(MEMORY_ID_MUST_BE_A_STRING);
        }
    }
}
