package com.estudo;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;

public class DocumentRetriever {

    public static ContentRetriever retriever() {
        return EmbeddingStoreContentRetriever
                .builder()
                .embeddingStore(RagStore.getStore())
                .embeddingModel(RagStore.getEmbeddings())
                .maxResults(3)
                .minScore(0.7)
                .build();
    }

}
