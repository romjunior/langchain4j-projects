package com.estudo.queue;

import com.estudo.llm.Agent;
import com.estudo.memory.ParkinglotChatMemoryStore;
import io.quarkiverse.langchain4j.runtime.aiservice.GuardrailException;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class MessageProcessor {

    private final Agent agent;

    private final ParkinglotChatMemoryStore parkinglotChatMemoryStore;

    public MessageProcessor(Agent agent,
                            ParkinglotChatMemoryStore parkinglotChatMemoryStore) {
        this.agent = agent;
        this.parkinglotChatMemoryStore = parkinglotChatMemoryStore;
    }

    @Blocking
    @Incoming("messages-in")
    @Outgoing("messages-out")
    @ActivateRequestContext
    public MessageOut process(Message<JsonObject> event) {
        final var message = event.getPayload().mapTo(MessageIn.class);
        Log.info("Message received: " + message);
        try {
            final var result = agent.chat(message.getSessionId(), message.getMessage());
            Log.info("result=" + result);
            event.ack();
            return MessageOut.of(message.getSessionId(), result);
        } catch (GuardrailException e) {
            if(e.getMessage().contains("CheckContentFailed")) {
                event.ack();
                return MessageOut.of(message.getSessionId(), "Me desculpe mas eu não posso falar sobre qualquer outro assunto além da gestão do estacionamento que eu faço, o que você precisa?");
            } else {
                Log.error("ErrorMessage=", e);
                event.ack();
                return MessageOut.of(message.getSessionId(), "Desculpe, eu estou indisponivel, pode voltar mais tarde?");
            }
        }
    }

}
