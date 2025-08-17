package org.estudo.tools;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import org.estudo.repository.parkingspace.ParkingSpace;
import org.estudo.service.ParkingSpaceService;

public class ParkingSpaceTool {

    private final ParkingSpaceService parkingSpaceService;

    public ParkingSpaceTool(ParkingSpaceService parkingSpaceService) {
        this.parkingSpaceService = parkingSpaceService;
    }

    @Tool(description = "Obtem lista de vagas disponíveis do estacionamento")
    String getAllAvailableParkingSpaces() {
        try {
            final var spaces = parkingSpaceService.getAllAvailableParkingSpaces();
            return "As vagas disponiveis são: " + spaces
                    .stream()
                    .map(ParkingSpace::getCode)
                    .reduce("", (p1, p2) -> p1.isEmpty() ? p2 : p1 + ", " + p2);
        } catch (Exception ex) {
            return "Não foi possível obtem a lista de vagas disponíveis";
        }
    }

    @Tool(description = "Validar o formato da placa que foi passada é válido ou não")
    public String isValidPlate(@ToolArg(description = "Placa do veiculo") String plate) {
        if(plate.matches("[A-Z]{3}-\\d{4}")) {
            return "Valido";
        } else {
            return "Invalido";
        }
    }
}
