package com.estudo.queue;

import com.estudo.llm.Assistant;
import io.quarkiverse.langchain4j.runtime.aiservice.GuardrailException;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class MessageInProcessor {

    private final Assistant assistant;

    public MessageInProcessor(Assistant assistant) {
        this.assistant = assistant;
    }

    @Incoming("messages-in")
    @Blocking
    @ActivateRequestContext
    public void process(JsonObject jsonObject) {
        final var message = new MessageIn(jsonObject.getString("memoryId"), jsonObject.getString("message"));
        Log.info("Message received: " + message);
        try {
            final String result = assistant.chat(message.memoryId(), message.message());
            Log.info("result=" + result);
        } catch (GuardrailException e) {
            if(e.getMessage().contains("CheckContentFailed")) {
                Log.info("Me desculpe mas eu não posso falar sobre qualquer outro assunto além da gestão do estacionamento que eu faço, o que você precisa?");
            } else {
                throw e;
            }
        }
    }

}
