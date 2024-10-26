package com.estudo;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;

public class RagStore {

    private static EmbeddingStore<TextSegment> embeddingStore;

    private static EmbeddingModel embeddingModel;

    static EmbeddingStore<TextSegment> create(
            String host,
            int port,
            String database,
            String user,
            String password,
            String table,
            int dimension
    ) {
        embeddingStore = PgVectorEmbeddingStore.builder()
                .host(host)
                .port(port)
                .database(database)
                .user(user)
                .password(password)
                .table(table)
                .dimension(dimension)
                .build();
        embeddingModel = new AllMiniLmL6V2EmbeddingModel();
        return embeddingStore;
    }

    static EmbeddingStore<TextSegment> getStore() {
        return embeddingStore;
    }

    static EmbeddingModel getEmbeddings() {
        return embeddingModel;
    }


}
