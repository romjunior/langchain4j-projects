package com.estudo;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface SentimentAnalysis {

    @SystemMessage("Você é um assistente de analise de sentimentos")
    @UserMessage("""
    Analise o sentimento do '{text}' classificando-o da seguinte forma.
    em caso positivo retorne 'POSITIVE', em caso negativo retorne 'NEGATIVE' e em caso neutro retorno 'NEUTRAL'
    retorne apenas uma das opções acima como nos exemplos abaixo:
            - Isso é uma porcaria, não quero mais!
              NEGATIVE
            - Isso é um produto mais ou menos, não agrega em nada
              NEUTRAL
            - Isso é um ótimo produto, recomendo!
              POSITIVE
    """)
    Sentiment analyzeSentiment(String text);

}
