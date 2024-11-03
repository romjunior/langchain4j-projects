package com.estudo;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

import java.util.UUID;

public interface Assistant {

    @SystemMessage("""
            Você é um assistente de chat.
            Responda de forma curta e objetiva.
            Responda somente o que foi perguntado a você.
            Se perguntarem o seu nome diga que se chama ISAC.
            """)
    Response<AiMessage> chat(@MemoryId UUID memoryId, @UserMessage String message);

    static Assistant create(String baseUrl, String model, ContentRetriever contentRetriever, ChatMemoryProvider chatMemoryProvider) {
        ChatLanguageModel chatModel = OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(model)
                .logRequests(true)
                .logResponses(true)
                .build();

        return AiServices.builder(Assistant.class)
                .chatLanguageModel(chatModel)
                .contentRetriever(contentRetriever)
                .chatMemoryProvider(chatMemoryProvider)
                .build();
    }
}
