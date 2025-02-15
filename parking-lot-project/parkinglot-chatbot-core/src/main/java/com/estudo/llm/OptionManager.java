package com.estudo.llm;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterAiService(
        chatMemoryProviderSupplier = RegisterAiService.NoChatMemoryProviderSupplier.class
)
public interface OptionManager {


    @SystemMessage(
            """
             Você vai analisar as mensagens da conversa você vai identificar o que o cliente deseja fazer e vai responder uma das opções da seguinte forma:
             
             Se o cliente falar que quer alocar uma vaga você vai responder: ALLOCATION
             Se o cliente falar que uer consultar uma alocação de vaga você vai responder: CONSULT_ALLOCATION
             Se o cliente falar que quer pagar a alocação da vaga você vai responder: PAYMENT
             Se você não identificar nenhuma das opções você vai responder: UNKOWN
         
             """
    )
    OptionStatus selectOption(@MemoryId String memoryId, @UserMessage String message);

}
