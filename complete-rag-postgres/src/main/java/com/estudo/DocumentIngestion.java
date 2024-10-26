package com.estudo;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DocumentIngestion {
    private final Logger logger = LoggerFactory.getLogger(DocumentIngestion.class);

    public void ingestDocument(Document document) {
        final var docSplitter = DocumentSplitters.recursive(300, 50);
        final var segments = docSplitter.split(document);
        final var responseEmbeddings = RagStore.getEmbeddings().embedAll(segments);
        logger.info("token-ingest={} stop-reason={} embeddings-size={}",
                responseEmbeddings.tokenUsage(),
                responseEmbeddings.finishReason(),
                responseEmbeddings.content().size());

        RagStore.getStore().addAll(responseEmbeddings.content(), segments);
    }
}
