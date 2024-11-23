package com.estudo.client.payment;


import dev.langchain4j.model.output.structured.Description;

public enum PaymentStatus {

    @Description("Pagamento Aprovado!")
    APPROVED,

    @Description("Já foi pago")
    ALREADY_PAIDED,

    @Description("Não existe a locação informada")
    NOT_EXISTS
}
