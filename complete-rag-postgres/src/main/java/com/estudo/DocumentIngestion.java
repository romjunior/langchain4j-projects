package com.estudo;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;


public final class DocumentIngestion {

    private static final Logger logger = LoggerFactory.getLogger(DocumentIngestion.class);

    private DocumentIngestion() {
    }

    public static void ingestDocument(Path path, RagStore ragStore) {
        final var documents =  getDocuments(path);
        final var segments = splitSegments(documents, 300, 50);
        final var responseEmbeddings = ragStore.getEmbeddings().embedAll(segments);
        logger.info("token-ingest={} stop-reason={} embeddings-size={}",
                responseEmbeddings.tokenUsage(),
                responseEmbeddings.finishReason(),
                responseEmbeddings.content().size());
        ragStore.getStore().addAll(responseEmbeddings.content(), segments);
    }

    static Document getDocuments(Path path) {
        DocumentParser documentParser = new TextDocumentParser();
        return FileSystemDocumentLoader.loadDocument(path, documentParser);
    }

    static List<TextSegment> splitSegments(Document documents, int segmentSize, int overlapSegment) {
        final var docSplitter = DocumentSplitters.recursive(segmentSize, overlapSegment);
        return docSplitter.split(documents);
    }
}
