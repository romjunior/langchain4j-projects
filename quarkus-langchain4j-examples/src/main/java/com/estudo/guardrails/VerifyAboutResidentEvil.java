package com.estudo.guardrails;

import dev.langchain4j.data.message.UserMessage;
import io.quarkiverse.langchain4j.guardrails.InputGuardrail;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailResult;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class VerifyAboutResidentEvil implements InputGuardrail {

    private final GuardRailExampleService guardRailExampleService;

    public VerifyAboutResidentEvil(GuardRailExampleService guardRailExampleService) {
        this.guardRailExampleService = guardRailExampleService;
    }

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        if(!guardRailExampleService.isAboutResidentEvil(userMessage.singleText())) {
            return failure("Desculpe, mas não é sobre o assunto de Resident Evil, então eu não consigo te ajudar :( \n me pergunte sobre Resident Evil que eu consigo lhe ajudar! :)");
        }
        return success();
    }
}
