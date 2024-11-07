package com.estudo;

import dev.langchain4j.model.output.structured.Description;

public enum Sentiment {

    @Description("Sentimento positivo")
    POSITIVE,
    @Description("Sentimento negativo")
    NEGATIVE,
    @Description("Sentimento neutro")
    NEUTRAL;
}
