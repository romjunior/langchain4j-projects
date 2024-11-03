package com.estudo;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;

public final class DocumentRetriever {

    private DocumentRetriever() {
    }

    public static ContentRetriever retriever(RagStore ragStore) {
        return EmbeddingStoreContentRetriever
                .builder()
                .embeddingStore(ragStore.getStore())
                .embeddingModel(ragStore.getEmbeddings())
                .maxResults(5)
                .minScore(0.7)
                .build();
    }

}
