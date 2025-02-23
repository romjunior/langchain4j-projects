package com.estudo.llm.guardrails;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(
        chatMemoryProviderSupplier = RegisterAiService.NoChatMemoryProviderSupplier.class
)
public interface ValidatePriceOutputGuardRails {

    @UserMessage(
            """
           Você vai validar os preços, se estiver correto vai responder 'false'
           Se estiver errado vai responder 'true'
           Se mencionar ou pedir para confirmar os preços na mensagem mas os valores não estiverem nela, vai retornar 'true'
           
           os preços corretos são: First Hour R$ 8,00 e Other Hours R$ 7,00
           
           Exemplos:
           1. Você concorda com os preços e tarifas que eu lhe mostrei? Se sim, posso continuar com a alocação de vaga. Se não, por favor, me informe se você tem alguma objeção ou se deseja discutir os preços. (E se quiser, posso sugerir um plano de pagamento que combine as horas de estacionamento.)
              Resposta: true
           2. Os preços são R$ 5 reais a primeira hora e R$ 8 reais o restante
              Resposta: true
           3. Os preços são 7 reais por hora
              Resposta: true
           4. Os preços são R$ 4,00 a primeira hora e R$ 3,00 o restante
              Resposta: false
           5. Eu sou o assistente alex e estou pronto para te ajudar
              Resposta: false
           6. Obrigado por me informar que não deseja mais a alocação de vaga
              Resposta: false
           
           
            Mensagem:
            {{message}}
           """
    )
    boolean isPriceWrong(@V("message") String message);
}
