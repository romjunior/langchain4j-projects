package com.estudo.tools;

import com.estudo.client.parkingspace.ParkingSpaceClient;
import com.estudo.client.parkingspace.ParkingSpaceDTO;
import com.estudo.client.parkingspace.ParkingSpaceStatus;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class ParkingSpaceService {

    ParkingSpaceClient parkingSpaceClient;

    public ParkingSpaceService(@RestClient ParkingSpaceClient parkingSpaceClient) {
        this.parkingSpaceClient = parkingSpaceClient;
    }

    @Tool("Consulta de vagas disponiveis no estacionamento")
    public List<String> getAvailableParkingSpaces() {
        return parkingSpaceClient.getAllParkingSpaces()
                .stream().filter(parkingSpaceDTO -> parkingSpaceDTO.status().equals(ParkingSpaceStatus.AVAILABLE))
                .map(ParkingSpaceDTO::code)
                .toList();
    }

    @Tool("Validar o formato da placa que foi passada é válido ou não")
    public boolean isValidPlate(String plate) {
        return plate.matches("[A-Z]{3}-\\d{4}");
    }
}
