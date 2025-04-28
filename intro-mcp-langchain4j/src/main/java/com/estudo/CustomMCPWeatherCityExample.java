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

import java.io.IOException;
import java.util.List;

public class CustomMCPWeatherCityExample {

    static final Logger log = LoggerFactory.getLogger(CustomMCPWeatherCityExample.class);

    interface Assistant {

        @SystemMessage("""
                Você é um assistente que busca a temperatura dado uma cidade e você deve fazer isso e responder ao cliente de forma amigável
                """)
        String chat(String text);
    }

    public static void main(String[] args) throws IOException {
        final var baseUrl = System.getenv("BASE_URL");
        final var modelName = System.getenv("MODEL_NAME");

        log.info("staring MCP config with custom-mcp-weather example");

        //do this to kill subprocess when the java process exits
        try(McpTransport transport = new StdioMcpTransport.Builder()
                .command(List.of("/home/junior/.asdf/program/asdf", "exec", "java", "-jar", "custom-mcp-weather-server-0.0.1.jar"))
                .logEvents(true)
                .build()) {

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

            final var chat = AiServices.builder(Assistant.class)
                    .chatLanguageModel(model)
                    .toolProvider(toolProvider)
                    .build();

            log.info("starting chat");

            String response = chat.chat("qual é a temperatura atual da cidade de Caieiras?");
            log.info("response={}", response);
        }
    }
}
