package com.estudo.llm;

import com.estudo.guardrails.CheckContent;
import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(
        chatMemoryProviderSupplier = RegisterAiService.NoChatMemoryProviderSupplier.class
)
public interface GuardRailsAssistant {


    @SystemMessage("""
            Você vai responder se está muito ou pouco relacionado ao assunto de estacionamento de carros e motos.
            Você vai responder apenas com um numero de 1 a 10, sendo 10 muito relacionado e 1 pouco relacionado.
            Você vai responder apenas com um numero e também precisa explicar o motivo do por que você deu o numero do relacionado.
            Além disso você deve levar consideração agradecimentos, comprimentos, elogios, preços de alocação da vaga, alocação de vaga também para serem altamente relacionado.
            E também você deve levar consideração somente estacionamento de carros, não pode relacionar com filmes, séries ou desenhos.
            """)
    CheckContent checkContentAbout(String message);
}
