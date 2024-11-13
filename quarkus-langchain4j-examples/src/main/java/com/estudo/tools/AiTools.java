package com.estudo.tools;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(
        tools = { MathTools.class }
)
public interface AiTools {

    @SystemMessage("""
            Caso possua mais de dois números para fazer a operação, quebre em operações de dois numeros e chame a função mais de uma vez,
            leve em consideração que a ordem das operações é importante, por exemplo: multiplicação e divisão são mais prioritárias que adição e subtração, então elas devem ser feitas primeiro
            Faça uma operação por vez chamando a tool certa e espere o resultado, assim que houver o resultado da operação anterior, faça a próxima operação até chegar no resultado final
            """)
    String chat(@UserMessage String message);
}
