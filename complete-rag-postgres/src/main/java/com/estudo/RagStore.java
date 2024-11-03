package com.estudo;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;

public class RagStore {

    private final EmbeddingStore<TextSegment> embeddingStore;

    private final EmbeddingModel embeddingModel;

    private RagStore(
            String host,
            int port,
            String database,
            String user,
            String password,
            String table,
            int dimension,
            EmbeddingModel embeddingModel
    ) {
        this.embeddingStore = PgVectorEmbeddingStore.builder()
                .host(host)
                .port(port)
                .database(database)
                .user(user)
                .password(password)
                .table(table)
                .dimension(dimension)
                .build();
        this.embeddingModel = embeddingModel;
    }

    public static RagStore of(
            String host,
            int port,
            String database,
            String user,
            String password,
            String table,
            int dimension
    ) {
        return new RagStore(host, port, database, user, password, table, dimension, new AllMiniLmL6V2EmbeddingModel());
    }

    EmbeddingStore<TextSegment> getStore() {
        return embeddingStore;
    }

    EmbeddingModel getEmbeddings() {
        return embeddingModel;
    }


}
