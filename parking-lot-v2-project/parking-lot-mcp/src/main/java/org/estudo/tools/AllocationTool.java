package org.estudo.tools;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import org.estudo.service.AllocationService;

public class AllocationTool {

    private final AllocationService allocationService;

    public AllocationTool(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @Tool(description = "Criar alocação do veiculo para a vaga selecionada")
    String createAllocation(
            @ToolArg(description = "Código da vaga") String parkingSpaceCode,
            @ToolArg(description = "placa do veiculo") String carPlate,
            @ToolArg(description = "cor do veiculo") String carColor
    ) {
        try {
            final var result = allocationService.createAllocation(parkingSpaceCode, carPlate, carColor);
            if(result) {
                return "Alocação criada com sucesso!";
            } else {
                return "Alocação não foi criada pois a vaga " + parkingSpaceCode + " não está disponível ou não existe";
            }
        } catch (Exception ex) {
            return "Não foi possível criar alocação do veiculo de placa" + carPlate + " para a vaga " + parkingSpaceCode;
        }
    }

    @Tool(description = "consultar situação da alocação pela placa do veiculo")
    String getAllocationByPlate(@ToolArg(description = "placa do veiculo") String carPlate) {
        try {
            final var result = allocationService.getAllocationByCarPlate(carPlate);
            if(result != null) {
                return "Situação da alocação => placa do veiculo: " + result.getCarPlate() + ", cor do veiculo: " + result.getCarColor() + ", código da vaga: " + result.getParkingSpaceCode() +  ", hora que entrou: " + result.getEntryDate().toString();
            } else {
                return "Alocação não existe ou não foi encontrada";
            }
        }catch (Exception ex) {
            return "Não foi possivel consultar a situação da alocação para a placa do veiculo " + carPlate;
        }
    }
}
