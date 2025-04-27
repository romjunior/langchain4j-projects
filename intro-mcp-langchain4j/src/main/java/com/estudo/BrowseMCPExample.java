package com.estudo;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.tool.ToolProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BrowseMCPExample {

    static final Logger log = LoggerFactory.getLogger(FileSystemExample.class);

    interface Assistant {

        @SystemMessage("""
                Você é um assistente e deve fazer somente o que o usuário pedir, seja direto na resposta
                """)
        String chat(String text);
    }

    public static void main(String[] args) {
        final var baseUrl = System.getenv("BASE_URL");
        final var modelName = System.getenv("MODEL_NAME");

        log.info("staring MCP config with browser-mcp example");

        McpTransport transport = new StdioMcpTransport.Builder()
                .command(List.of("/home/junior/.asdf/program/asdf", "exec", "npx", "@browsermcp/mcp@latest"))
                .logEvents(true)
                .build();

        log.info("starting MCP Client");

        McpClient mcpClient = new DefaultMcpClient.Builder()
                .transport(transport)
                .logHandler(new MyLogHandler())
                .build();

        log.info("starting Tool Provider");

        ToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(List.of(mcpClient))
                .failIfOneServerFails(true)
                .build();

        log.info("starting model config");

        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .temperature(0.5)
                .logRequests(true)
                .logResponses(true)
                .build();

        log.info("starting assistant");

        final var chat = AiServices.builder(FileSystemExample.Assistant.class)
                .chatLanguageModel(model)
                .toolProvider(toolProvider)
                .build();

        log.info("starting chat");

        String response = chat.chat("acesse o https://statusinvest.com.br/acoes/{código da ação}, coloque no código da ação ITSA4 após isso leia HTML e encontre as tags com o valor atual e me retorne o preço descrito");
        log.info("response={}", response);
    }
}
