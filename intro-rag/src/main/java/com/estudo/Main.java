package com.estudo;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.internal.Utils;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    interface Assistant {

        @SystemMessage(
                """
                        Você é um assistente e vai responder as perguntas de acordo com o que foi passado para você, responda somente o necessário, evique coisas como:
                        
                        1. de acordo com o que mencionou
                        
                        2. de acordo com as informações passadas
                        
                        Também se não tiver informações sobre o que responder, você vai responder exatamente 'eu não sei'
                        """
        )
        String chat(String query);
    }

    public static void main(String[] args) {
        final var baseUrl = System.getenv("BASE_URL");
        final var modelName = System.getenv("MODEL_NAME");

        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .temperature(0.1)
                .logResponses(true)
                .logRequests(true)
                .build();

        DocumentParser documentParser = new TextDocumentParser();

        Document document = FileSystemDocumentLoader.loadDocument(toPath("documents/history.txt"), documentParser);

        DocumentSplitter documentSplitter = DocumentSplitters.recursive(300, 50);
        List<TextSegment> segments = documentSplitter.split(document);

        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        List<Embedding> embeddings = embeddingModel.embedAll(segments).content();

        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        embeddingStore.addAll(embeddings, segments);

        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever
                .builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5)
                .minScore(0.7)
                .build();

        ChatMemory chatWithRAGMemory = MessageWindowChatMemory.withMaxMessages(10);


        final var asswithrag = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .contentRetriever(contentRetriever)
                .chatMemory(chatWithRAGMemory)
                .build();

        final var asswithoutrag = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .build();

        final var query = "joao atua focados com o que?";

        System.out.println(contentRetriever.retrieve(Query.from(query)));

        System.out.println("Pergunta: " + query);
        System.out.println("[Sem RAG]: " + asswithoutrag.chat(query));
        System.out.println("[Com RAG]: " + asswithrag.chat(query));



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