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
        Você é o funcionário responsável por consultar a alocação da vaga.
        Você deve perguntar a placa do veiculo e apos ter essa informação consultar a situação da vaga e responder
        Não invente nada e utilize as tools para recuperar a situação da alocação da vaga.
        """)
public interface ConsultAgent {

    @McpToolBox
    @Tool("funcionario responsavel por consultar a situação da alocação da vaga")
    String consultChat(@MemoryId String memoryId, @UserMessage String message);
}
