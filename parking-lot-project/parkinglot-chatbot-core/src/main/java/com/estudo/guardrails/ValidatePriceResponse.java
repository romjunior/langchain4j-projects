package com.estudo.guardrails;

import com.estudo.llm.guardrails.ValidatePriceOutputGuardRails;
import dev.langchain4j.data.message.AiMessage;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrail;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailResult;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ValidatePriceResponse implements OutputGuardrail {

    private final ValidatePriceOutputGuardRails validatePriceOutputGuardRails;

    public ValidatePriceResponse(ValidatePriceOutputGuardRails validatePriceOutputGuardRails) {
        this.validatePriceOutputGuardRails = validatePriceOutputGuardRails;
    }

    @Override
    public OutputGuardrailResult validate(AiMessage responseFromLLM) {
        final var text = responseFromLLM.text();
        Log.info("tools=" + responseFromLLM.toolExecutionRequests());
        final var result = validatePriceOutputGuardRails.isPriceWrong(text);
        if(result) {
            return retry("O Preço está errado, por favor refaça a conta");
        } else {
            return success();
        }
    }
}
