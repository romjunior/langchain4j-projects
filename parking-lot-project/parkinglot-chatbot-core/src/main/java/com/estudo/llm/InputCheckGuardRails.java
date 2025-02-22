package com.estudo.llm;

import com.estudo.guardrails.CheckContent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(
        chatMemoryProviderSupplier = RegisterAiService.NoChatMemoryProviderSupplier.class
)
public interface InputCheckGuardRails {


    @UserMessage("""
            Você vai responder se a conversa que está delimitado entre --- está muito ou pouco relacionado ao assunto de estacionamento de carros.
            Você vai responder apenas com um numero de 1 a 10, sendo 10 muito relacionado e 1 pouco relacionado.
            Você vai responder apenas com um numero e também precisa explicar o racionio(como você chegou a conclusão) da nota de relacionamento.
            Além disso você deve levar consideração agradecimentos, comprimentos, elogios, preços de alocação da vaga, alocação de vaga também para serem altamente relacionado.
            E também você deve levar consideração somente estacionamento de carros, não pode relacionar com filmes, séries ou desenhos.
            também se o historico tiver oi, ola, bom dia, boa tarde e boa noite, isso são comprimentos e devem ter classificação alta
            
            ---
            {{message}}
            ---
            """)
    CheckContent checkContentAbout(@V("message") String message);
}
