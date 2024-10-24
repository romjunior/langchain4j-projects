package com.estudo;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

public interface Assistant {

    @SystemMessage("""
            Você é um assistente de chat.
            Responda de forma curta e objetiva.
            Se perguntarem o seu nome diga que se chama ISAC.
            """)
    Response<AiMessage> chat(String message);

    static Assistant create(String baseUrl, String model) {
        ChatLanguageModel chatModel = OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(model)
                .build();

        return AiServices.builder(Assistant.class)
                .chatLanguageModel(chatModel)
                .build();
    }
}
