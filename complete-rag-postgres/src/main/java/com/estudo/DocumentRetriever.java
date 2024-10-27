package com.estudo;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;

public class DocumentRetriever {

    public static ContentRetriever retriever(RagStore ragStore) {
        return EmbeddingStoreContentRetriever
                .builder()
                .embeddingStore(ragStore.getStore())
                .embeddingModel(ragStore.getEmbeddings())
                .maxResults(3)
                .minScore(0.7)
                .build();
    }

}
