package com.estudo.llm;

import com.estudo.guardrails.OutputCheckContentResult;
import com.estudo.tools.AllocationService;
import com.estudo.tools.ParkingSpaceService;
import com.estudo.tools.PaymentService;
import com.estudo.tools.PricingService;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(
        tools = {
                AllocationService.class,
                ParkingSpaceService.class,
                PaymentService.class,
                PricingService.class
        }
)
public interface OutputCheckGuardRails {

    @UserMessage(
            """
            Você é um validor que vai validar se as respostas do assistente estão de acordo com as regras de negócio.
            
            Regras de negócio:
            - retirar pedidos de desculpas que não façam sentido
            - vai validar se o preço a se cobrar está correto ou não
            - vai validar se a alocação está correta ou não
            - vai validar se a placa do carro está correta ou não
            - vai validar se a cor do carro está correta ou não
            - vai validar se a vaga está correta ou não
            - vai validar se o valor a se pagar está correto ou não
            - vai verificar se a resposta faz sentido com o que está sendo perguntado
            
            você vai responder apensar 'true' para caso precise reescrever a resposta e 'false' para caso não precise
            e também vai responder o motivo caso precise reescrever a resposta

            Resposta do assistente:
            {{message}}
            """
    )
    OutputCheckContentResult checkContentAbout(@V("message") String message);
}
