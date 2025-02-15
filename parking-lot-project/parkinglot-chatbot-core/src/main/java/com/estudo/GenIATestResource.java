package com.estudo;

import com.estudo.llm.Assistant;
import com.estudo.llm.OptionManager;
import com.estudo.llm.OptionStatus;
import io.quarkiverse.langchain4j.runtime.aiservice.GuardrailException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/genia")
public class GenIATestResource {

    private final Assistant assistant;

    private final OptionManager optionManager;

    public GenIATestResource(Assistant assistant, OptionManager optionManager) {
        this.assistant = assistant;
        this.optionManager = optionManager;
    }

    @POST
    @Path("/{memoryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String chat(@PathParam("memoryId") String memoryId, ChatBody chatBody) {
        try {

            final var option = optionManager.selectOption(memoryId, chatBody.message());

            if(option == OptionStatus.UNKOWN) {
                return """
                         Olá! eu sou o assistente virtual do estacionamento, como posso ajudar?
                        Você pode me dizer:
                        - Quero alocar uma vaga
                        - Quero consultar uma alocação
                        - Quero pagar a alocação
                        """;
            } else {
                return assistant.chat(memoryId, chatBody.message());
            }
        } catch (GuardrailException e) {
            if(e.getMessage().contains("CheckContentFailed")) {
                return "Me desculpe mas eu não posso falar sobre qualquer outro assunto além da gestão do estacionamento que eu faço, o que você precisa?";
            } else {
                throw e;
            }
        }
    }

    record ChatBody(String message) {}
}
