package com.estudo;


import io.smallrye.common.annotation.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class MessageOutProcessor {

    private final SessionManager sessionManager;

    public MessageOutProcessor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Blocking
    @Incoming("messages-out")
    @ActivateRequestContext
    public CompletionStage<Void> process(Message<JsonObject> event) {
        final var message = event.getPayload().mapTo(MessageOut.class);
        sessionManager.sendMessage(message);
        return event.ack();
    }
}
