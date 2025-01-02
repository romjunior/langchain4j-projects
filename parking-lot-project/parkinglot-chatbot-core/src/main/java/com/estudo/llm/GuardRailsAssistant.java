package com.estudo.llm;

import com.estudo.guardrails.CheckContent;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(
        chatMemoryProviderSupplier = RegisterAiService.NoChatMemoryProviderSupplier.class
)
public interface GuardRailsAssistant {


    @UserMessage("""
            Você vai responder se a conversa que está delimitado entre --- está muito ou pouco relacionado ao assunto de estacionamento de carros.
            Você vai responder apenas com um numero de 1 a 10, sendo 10 muito relacionado e 1 pouco relacionado.
            Você vai responder apenas com um numero e também precisa explicar o racionio(como você chegou a conclusão) da nota de relacionamento.
            Além disso você deve levar consideração agradecimentos, comprimentos, elogios, preços de alocação da vaga, alocação de vaga também para serem altamente relacionado.
            E também você deve levar consideração somente estacionamento de carros, não pode relacionar com filmes, séries ou desenhos.
            
            ---
            {{message}}
            ---
            """)
    CheckContent checkContentAbout(String message);
}
