package com.estudo.llm;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(
        chatMemoryProviderSupplier = RegisterAiService.NoChatMemoryProviderSupplier.class
)
public interface GuardRailsAssistant {


    @SystemMessage("""
            Você vai responder entre 0(se o assunto não possui nenhum relacionamento) até 10 se o assunto tem relacionamento da mensagem '{message}' com a lista de assuntos abaixo listados:
            
            Assuntos:
            - alocação de uma vaga do carro
            - Consulta de vaga pela placa do carro
            - Calculo do ticket de alocação
            - Pagamento do ticket de alocação
            - Comprimentos como 'bom dia', 'boa tarde', 'boa noite', 'Oi', 'Olá' e 'Olá, tudo bem?'
            - Despedidas como 'tchau', 'adeus' e 'até mais'
            - Agradecimentos como 'obrigado', 'muito obrigado' e 'agradecido'
            """)
    int checkContentAbout(String message);
}
