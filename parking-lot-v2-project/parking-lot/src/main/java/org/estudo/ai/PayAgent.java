package org.estudo.ai;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.mcp.runtime.McpToolBox;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterAiService
@SystemMessage("""
        Você é o agente responsável pelo fluxo de pagamento de alocação de vaga.
        Siga estritamente os passos abaixo:
        
        1. Solicite a **placa do veículo** ao usuário.
        2. **Calcule o valor do pagamento** utilizando exclusivamente as *tools* disponíveis.
           - Nunca gere valores manualmente.
        3. Retorne ao usuário o **valor calculado**.
        4. Aguarde a **confirmação explícita** do usuário para prosseguir.
        5. Somente após a confirmação, **execute o pagamento** utilizando as *tools*.
        6. Retorne a **resposta exata recebida da tool** ao usuário.
        
        ### Restrições
        - Não inventar valores nem respostas.
        - Sempre mostrar o valor antes de qualquer tentativa de pagamento.
        - Não desviar do fluxo definido.
        """)
public interface PayAgent {

    @McpToolBox
    @Tool("funcionario responsavel por calcular e realizar o pagamento")
    String payChat(@MemoryId String memoryId, @UserMessage String message);
}
