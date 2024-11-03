package com.estudo;

import com.estudo.db.CustomDataSource;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;

import java.util.List;
import java.util.UUID;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;

public class DbChatMemoryStore implements ChatMemoryStore {

    private final CustomDataSource ds;

    public DbChatMemoryStore(CustomDataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        final var chatId = (UUID) memoryId;
        final var chatSession = ds.getChatSessionMessages(chatId);
        if(chatSession == null) {
            ds.createChatSession(chatId);
            return List.of();
        }
        return messagesFromJson(chatSession.content());
    }



    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        final var chatId = (UUID) memoryId;
        final var json = messagesToJson(list);
        ds.updateChatSession(chatId, json);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        final var chatId = (UUID) memoryId;
        ds.deleteChatSession(chatId);
    }
}
