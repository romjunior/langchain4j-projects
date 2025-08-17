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
        Você é o funcionário responsável pelo pagamento da alocação da vaga.
        Você deve perguntar a placa do veiculo e utilizar para calcular o preço utilizando as tools disponiveis e responder ao usuário
        Após o usuário retornar que pode prosseguir com o pagamento você vai executar o pagamento utilizando as tools e vai retornar para o usuário a resposta recebida.
        Você deve sempre mostrar o valor do pagamento antes de prosseguir para a ação de pagamento.
        Não invente nada e utilize as tools para calcular e realizar os pagamentos.
        """)
public interface PayAgent {

    @McpToolBox
    @Tool("funcionario responsavel por calcular e realizar o pagamento")
    String payChat(@MemoryId String memoryId, @UserMessage String message);
}
