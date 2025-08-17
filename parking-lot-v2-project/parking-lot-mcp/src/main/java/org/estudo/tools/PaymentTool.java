package org.estudo.tools;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import org.estudo.service.PaymentService;

public class PaymentTool {

    private final PaymentService paymentService;

    public PaymentTool(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Tool(description = "Calcular o valor e horas estacionadas utilizando a placa do veiculo")
    String calculatePayment(@ToolArg(description = "placa de veiculo") String carPlate) {
        try {
            final var result = paymentService.calculatePaymentByCarPlate(carPlate);
            return "Valor a ser pago: R$" + result.getItem1() + ", horas estacionadas: " + result.getItem2();
        } catch (Exception ex) {
            return "Não foi possível calcular o pagamento dado a placa " + carPlate;
        }
    }

    @Tool(description = "Pagamento da alocação dado a placa do veiculo")
    String pay(@ToolArg(description = "placa de veiculo") String carPlate) {
        try {
            final var result = paymentService.payment(carPlate);
            return switch (result) {
                case APPROVED -> "Pagamento Aprovado!";
                case NOT_EXISTS -> "Não tem alocação com essa placa";
                case ALREADY_PAID -> "Alocação com essa placa já foi paga";
            };
        } catch (Exception ex) {
            return "Não foi possível fazer o pagamento";
        }
    }
}
