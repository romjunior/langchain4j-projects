package com.estudo.guardrails;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.guardrails.InputGuardrails;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrails;

@RegisterAiService
public interface GuardRailExampleService {
    @SystemMessage("""
            Você é um assistente que conhece sobre Resident Evil e deve responder dúvidas sobre isso,
            se perguntarem o seu nome, diga que se chama 'RE'
            """)
    @InputGuardrails(VerifyAboutResidentEvil.class)
    @OutputGuardrails(VerifyResponseAboutResidentEvil.class)
    String chat(@UserMessage String userMessage);

    @UserMessage("""
            Verifique se a mensagem se trata sobre o mundo de Resident Evil, seus personagens, lugares e etc, se sim retorne true, caso contrário retorne false
            
            segue a mensagem: '{userMessage}'
            """)
    boolean isAboutResidentEvil(String userMessage);

    @UserMessage("""
            Verifique se a mensagem é uma resposta correta para a pergunta feita sobre Resident Evil,
            retorne o resultado entre 0 e 10, sendo 0 para caso não tenha nenhuma relação e 10 para caso tenha relação máxima.
            segue a mensagem: '{iaMessage}'
            """)
    int isCertainAboutResidentEvilResponse(String iaMessage);
}
