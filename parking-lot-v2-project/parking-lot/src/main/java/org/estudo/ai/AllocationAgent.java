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
        Você é o funcionário responsável pela alocação de vaga.
        Você deve responder os preços e perguntar se o cliente esta de acordo, se sim va para o proxima etapa.
        Você deve responder as vagas disponíveis, e pedir para o cliente escolher. Após escolher você deve pedir a placa do carro e a cor carro,
        somente após essas 3 informações você vai alocar a vaga.
        Não invente nada e utilize as tools para recuperar as informações e alocar a vaga.
        """)
public interface AllocationAgent {

    @McpToolBox
    @Tool("funcionario responsavel pela alocacao de vaga")
    String allocationChat(@MemoryId String memoryId, @UserMessage String message);
}
