package com.estudo.tools;

import com.estudo.client.allocation.AllocationClient;
import com.estudo.client.allocation.AllocationDTO;
import com.estudo.client.allocation.CreateAllocationDTO;
import dev.langchain4j.agent.tool.Tool;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

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

        if(parkingSpaceCode == null || parkingSpaceCode.isEmpty() ||
                carPlate == null || carPlate.isEmpty() ||
                carColor == null || carColor.isEmpty()) {
            throw new RuntimeException("Código da vaga, placa do carro ou cor do carro são obrigatórias!");
        }

        Log.info(String.format("M=createAllocation parkingCode=%s carPlate=%s carColor=%s", parkingSpaceCode, carPlate, carColor));
        try {
            allocationClient.createAllocation(new CreateAllocationDTO(parkingSpaceCode, carPlate, carColor));
        } catch (ClientWebApplicationException ex) {
            return false;
        }
        return true;
    }

    @Tool("""
    Consulta de alocação da vaga para o estacionamento.

    Parametros:
    carPlate: Placa do carro

    Retorno:
    informações sobre a alocação da vaga
    """)
    public AllocationDTO getAllocationByCarPlate(String carPlate) {
        Log.info(String.format("M=getAllocationByCarPlate carPlate=%s", carPlate));
        return allocationClient.getAllocationByCarPlate(carPlate);
    }
}
