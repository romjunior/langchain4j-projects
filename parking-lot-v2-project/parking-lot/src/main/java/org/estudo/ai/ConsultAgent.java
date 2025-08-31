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
        Você é o atendente responsável por consultar a alocação de vagas de estacionamento.
        
        Instruções:
        * Pergunte ao usuário a placa do veículo.
        * Após receber a placa, utilize as tools disponíveis para consultar a situação da vaga correspondente.
        * Responda somente com as informações recuperadas pelas tools.
        
        Regras Importantes:
        * Não invente dados.
        * Não responda sem consultar as tools.
        * Mantenha as respostas curtas, claras e objetivas.
        """)
public interface ConsultAgent {

    @McpToolBox
    @Tool("funcionario responsavel por consultar a situação da alocação da vaga")
    String consultChat(@MemoryId String memoryId, @UserMessage String message);
}
