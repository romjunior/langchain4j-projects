package com.estudo.guardrails;

import com.estudo.llm.GuardRailsAssistant;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import io.quarkiverse.langchain4j.guardrails.InputGuardrail;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailParams;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailResult;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InputCheckContent implements InputGuardrail {

    private final GuardRailsAssistant guardRailsAssistant;

    public InputCheckContent(GuardRailsAssistant guardRailsAssistant) {
        this.guardRailsAssistant = guardRailsAssistant;
    }

    @Override
    public InputGuardrailResult validate(InputGuardrailParams params) {
        var text = params.memory().messages()
                .stream()
                .map(this::convertMessagesToString)
                .filter(texts -> !texts.isEmpty())
                .reduce("", (a, b) -> a + "\n" + b);

        text = text + "\n" + "User: " + params.userMessage().singleText();

        Log.info("text=" + text);

        final var result = guardRailsAssistant.checkContentAbout(text);
        Log.info("result=" + result.relation() + ", reason=" + result.reasoning());
        if (result.relation() > 7) {
            return success();
        } else {
            return fatal("CheckContentFailed");
        }
    }

    String convertMessagesToString(ChatMessage message) {
        return switch (message) {
            case UserMessage userMessage -> "User: " + userMessage.singleText();
            case AiMessage aiMessage -> aiMessage.text() == null ? "" : "Assistant: " + aiMessage.text();
            default -> "";
        };
    }
}
