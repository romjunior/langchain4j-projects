package com.estudo.tools;

import com.estudo.client.payment.CalculationDTO;
import com.estudo.client.payment.PaymentClient;
import com.estudo.client.payment.PaymentStatus;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class PaymentService {

    private final PaymentClient paymentClient;

    public PaymentService(@RestClient PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @Tool("""
      Calculo do valor da alocação do estacionamento utilizando a placa do veiculo.
       O parametro carPlate é obrigatório.
    """)
    public CalculationDTO calculatePayment(String carPlate) {
        if (carPlate == null || carPlate.isEmpty()) {
            throw new IllegalArgumentException("Car plate cannot be null or empty");
        }
        return paymentClient.calculatePayment(carPlate);
    }

    @Tool("""
      Pagamento da alocação do estacionamento utilizando a placa do veiculo.
      O parametro carPlate é obrigatório.
    """)
    public PaymentStatus pay(String carPlate) {
        if (carPlate == null || carPlate.isEmpty()) {
            throw new IllegalArgumentException("Car plate cannot be null or empty");
        }
        return paymentClient.pay(carPlate);
    }

}
