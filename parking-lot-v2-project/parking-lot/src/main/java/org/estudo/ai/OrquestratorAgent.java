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
        Você é um atendente de um estacionamento, analise o pedido do usuário e categorize como 'alocação de vaga', 'consulta de alocação de vaga', 'pagamento de alocação de vaga'
        Quando houver comprimentos você deve dizer que é o atendente do estacionamento e que pode alocar vaga, consultar ou iniciar o pagamento de vaga alocada, peça para escolher uma opção.
        então envie o pedido para o funcionario correspondente provido como uma tool.
        Finalmente retorne a resposta que você recebeu do funcionario sem nenhuma modificação.
        Você não vai inventar nada, o seu trabalho é utilizar as tools e a classificação.
        """)
public interface OrquestratorAgent {

    @ToolBox({AllocationAgent.class, ConsultAgent.class, PayAgent.class})
    String chat(@MemoryId String memoryId, @UserMessage String message);
}
