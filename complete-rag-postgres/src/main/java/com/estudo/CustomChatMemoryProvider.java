package com.estudo;

import com.estudo.db.CustomDataSource;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

public interface CustomChatMemoryProvider {


    static ChatMemoryProvider of(CustomDataSource db) {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(new DbChatMemoryStore(db))
                .build();
    }

}
