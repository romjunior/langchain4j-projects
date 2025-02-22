package com.estudo.guardrails;

import com.estudo.llm.InputCheckGuardRails;
import com.estudo.memory.MessagesToStringConverter;
import io.quarkiverse.langchain4j.guardrails.InputGuardrail;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailParams;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailResult;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InputCheckContent implements InputGuardrail {

    private final InputCheckGuardRails inputCheckGuardRails;

    public InputCheckContent(InputCheckGuardRails inputCheckGuardRails) {
        this.inputCheckGuardRails = inputCheckGuardRails;
    }

    @Override
    public InputGuardrailResult validate(InputGuardrailParams params) {
        final var text = MessagesToStringConverter.convert(
                params.memory().messages(),
                params.userMessage().singleText()
        );

        Log.info("text=" + text);

        final var result = inputCheckGuardRails.checkContentAbout(text);
        Log.info("result=" + result.relation() + ", reason=" + result.reasoning());
        if (result.relation() > 7) {
            return success();
        } else {
            return fatal("CheckContentFailed");
        }
    }
}
