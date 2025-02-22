package com.estudo.guardrails;

import com.estudo.llm.OutputCheckGuardRails;
import dev.langchain4j.data.message.AiMessage;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrail;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OutputCheckResponse implements OutputGuardrail {

    private final OutputCheckGuardRails outputCheckGuardRails;

    public OutputCheckResponse(OutputCheckGuardRails outputCheckGuardRails) {
        this.outputCheckGuardRails = outputCheckGuardRails;
    }

    @Override
    public OutputGuardrailResult validate(AiMessage responseFromLLM) {
        final var text = responseFromLLM.text();
        final var result = outputCheckGuardRails.checkContentAbout(text);
        if(result.reprompt()) {
            return retry(result.whatToReprompt());
        } else {
            return success();
        }
    }
}
