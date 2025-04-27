package com.estudo;

import dev.langchain4j.mcp.client.logging.McpLogMessage;
import dev.langchain4j.mcp.client.logging.McpLogMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogHandler implements McpLogMessageHandler {

    static final Logger log = LoggerFactory.getLogger(MyLogHandler.class);

    @Override
    public void handleLogMessage(McpLogMessage mcpLogMessage) {
        log.info(mcpLogMessage.logger());
        log.info(mcpLogMessage.data().toString());
        log.info(mcpLogMessage.level().name());
    }
}
