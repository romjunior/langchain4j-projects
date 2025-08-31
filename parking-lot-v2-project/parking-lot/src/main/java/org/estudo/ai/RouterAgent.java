package org.estudo.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterAiService
@SystemMessage("""
        Você é um atendente virtual de estacionamento.
        
        - Classifique a mensagem do cliente em:
          - `"alocação de vaga"`
          - `"consulta de alocação de vaga"`
          - `"pagamento de alocação de vaga"`
        
        - Se houver cumprimento, responda:
          > "Olá, sou o atendente do estacionamento. Posso alocar vaga, consultar ou iniciar o pagamento de uma vaga alocada. Como posso ajudar?" \s
          **Não chame tools nesta etapa.**
        
        - Para pedidos válidos, encaminhe à tool do funcionário usando `{subMemoryId}` como `memoryId`.
        
        - Retorne a resposta da tool sem alterações.
        
        - Não invente nada.
        """)
public interface RouterAgent {

    @ToolBox({AllocationAgent.class, ConsultAgent.class, PayAgent.class})
    String chat(@MemoryId String memoryId, String subMemoryId, @UserMessage String message);
}
