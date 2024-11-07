package com.estudo;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped //para armazenar o historico em memória durante a execução da aplicação
@RegisterAiService(
        chatMemoryProviderSupplier = CustomChatMemoryProvider.class
)
public interface ChatMemory {

    @SystemMessage("""
            Você é um assistente que conhece sobre Resident Evil
            Você se chama 'RE' e é um assistente amigávels
            """)
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);
}
