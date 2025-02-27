package com.estudo;

import com.estudo.db.CustomDataSource;
import dev.langchain4j.internal.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.UUID;

public class Chatbot {

    public static final Logger logger = LoggerFactory.getLogger(Chatbot.class);

    public static void main(String[] args) {
        final var baseUrl = System.getenv("BASE_URL");
        final var model = System.getenv("MODEL_NAME");

        final var customDs = CustomDataSource.of(
                "localhost",
                "5432",
                "rag",
                "rag",
                "rag123"
        );

        final var chatId = UUID.randomUUID();

        final var scanner = new Scanner(System.in);

        String input = null;

        final var ragStore = RagStore.of("localhost",
                5432,
                "rag",
                "rag",
                "rag123",
                "contents",
                384
        );

        DocumentIngestion.ingestDocument(toPath("documents/detailed_pokemon_list.txt"), ragStore);

        final var assistant = Assistant.create(baseUrl, model, DocumentRetriever.retriever(ragStore),
                CustomChatMemoryProvider.of(customDs));

        System.out.println("=======ISAC==========");

        do {
            System.out.print("[user]:");
            input = scanner.nextLine();
            if(!input.equals("/sair")) {
                final var response = assistant.chat(chatId, input);
                System.out.printf("[token] %s\n", response.tokenUsage());
                System.out.println("[ISAC]: " + response.content().text());
            }
        } while (!input.equals("/sair"));
    }

    static Path toPath(String relativePath) {
        try {
            URL fileUrl = Utils.class.getClassLoader().getResource(relativePath);
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}