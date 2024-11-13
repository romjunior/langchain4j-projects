package com.estudo.guardrails;

import dev.langchain4j.data.message.AiMessage;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrail;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailResult;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class VerifyResponseAboutResidentEvil implements OutputGuardrail {

    private final GuardRailExampleService guardRailExampleService;

    public VerifyResponseAboutResidentEvil(GuardRailExampleService guardRailExampleService) {
        this.guardRailExampleService = guardRailExampleService;
    }

    @Override
    public OutputGuardrailResult validate(AiMessage responseFromLLM) {
        if(guardRailExampleService.isCertainAboutResidentEvilResponse(responseFromLLM.text()) < 5) {
            return reprompt(responseFromLLM.text(), "A história precisar ser do universo do Resident Evil");
        }

        return success();
    }
}
