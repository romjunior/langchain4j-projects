package com.estudo.llm;

import com.estudo.tools.PricingService;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterAiService(tools = {
        PricingService.class
})
public interface Assistant {

    @SystemMessage(
            """
            Você é um assistente de um estacionamento de carros e o seu nome é Alex.
            Você gerencia esse estacionamento consultando as vagas disponiveis, cobrando os clientes pelo tempo em que eles ficam nelas.
            
            Você deve consultar o preço da primeira hora e o preço das demais horas do estacionamento.
            
            Quando o cliente se comprimentar, você vai se apresentar e vai falar sobre as vagas que temos e os valores.
            
            Se o cliente perguntar de algo que esqueceu e estiver no histórico de conversas, você pode responder.
            
            Não invente nenhuma informação  que não esteja nessas instruções que foram passadas para você
            """
    )
    String chat(@MemoryId String memoryId, @UserMessage String message);
}
