package com.estudo.guardrails;

import com.estudo.llm.GuardRailsAssistant;
import dev.langchain4j.data.message.UserMessage;
import io.quarkiverse.langchain4j.guardrails.InputGuardrail;
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
    public InputGuardrailResult validate(UserMessage userMessage) {
        final var content = userMessage.singleText();
        final var result = guardRailsAssistant.checkContentAbout(content);
        Log.info("result=" + result.relation() + ", reason=" + result.reasoning());
        if (result.relation() > 7) {
            return success();
        } else {
            return fatal("CheckContentFailed");
        }
    }
}
