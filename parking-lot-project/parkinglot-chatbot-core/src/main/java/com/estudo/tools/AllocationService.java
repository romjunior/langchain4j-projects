package com.estudo.tools;

import com.estudo.client.allocation.AllocationClient;
import com.estudo.client.allocation.AllocationDTO;
import dev.langchain4j.agent.tool.Tool;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class AllocationService {

    private final AllocationClient allocationClient;

    public AllocationService(@RestClient AllocationClient allocationClient) {
        this.allocationClient = allocationClient;
    }

    @Tool("""
    Criação da alocação da vaga para o estacionamento.
    
    Parametros:
    parkingSpaceCode: Código da vaga
    carPlate: Placa do carro
    carColor: Cor do carro
    
    Retorno:
    retorna true se a alocação for criada com sucesso, false caso já esteja ocupada
    """)
    public boolean createAllocation(String parkingSpaceCode, String carPlate, String carColor) {
        Log.info(String.format("M=createAllocation parkingCode=%s carPlate=%s carColor=%s", parkingSpaceCode, carPlate, carColor));
        allocationClient.createAllocation(new AllocationDTO(parkingSpaceCode, carPlate, carColor));
        return true;
    }
}
