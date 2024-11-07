package com.estudo;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface MyIAService {
    String chat(@UserMessage String userMessage);
}
