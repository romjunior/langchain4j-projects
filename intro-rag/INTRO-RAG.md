# Projeto Intro RAG

## Sobre o Projeto
Este projeto é um exemplo de implementação de um chatbot utilizando a biblioteca LangChain4j. O objetivo do projeto é criar um assistente que responda perguntas com base em informações pré-definidas, além de utilizar uma técnica chamada Retrieval-Augmented Generation (RAG) para melhorar as respostas.

## Arquivo Main.java

## Passo a Passo
1. Importação de Bibliotecas: O arquivo Main.java importa várias bibliotecas relacionadas ao LangChain4j[^1], incluindo DocumentParser, DocumentSplitter, EmbeddingModel e ContentRetriever.
2. Definição da Modelo do Chatbot: A partir do modelo OllamaChatModel, é criado um chatbot com temperatura de 0.1 e habilitado a logar respostas e requisições.
3. Carregamento do Documento: O arquivo history.txt é carregado utilizando o FileSystemDocumentLoader.
4. Divisão do Documento em Segmentos: O documento é dividido em segmentos de 300 caracteres com intervalo de 50 caracteres entre eles.
5. Embedding dos Segmentos: Os segmentos são embutidos utilizando o AllMiniLmL6V2EmbeddingModel.
6. Armazenamento dos Embeddings: Os embeddings são armazenados em uma instância do InMemoryEmbeddingStore.
7. Definição da ContentRetriever: A partir do EmbeddingStoreContentRetriever, é definida uma ContentRetriever que busca respostas no banco de dados de embeddings.
8. Criação das Instâncias do Chatbot: São criadas instâncias do chatbot com e sem a utilização da RAG.
9. Execução do Chatbot: O chatbot é executado para responder à pergunta "joao atua focados com o que?".

## Sessão de Explicação
### Arquivo Main.java
* Importe as bibliotecas necessárias: DocumentParser, DocumentSplitter, EmbeddingModel e ContentRetriever
* Defina um modelo de chatbot usando o OllamaChatModel:
    * Defina temperatura, habilitar log de respostas e requisições
* Carregue o documento history.txt:
    * Utilize o FileSystemDocumentLoader
## OllamaChatModel
* Defina modelo do chatbot com base em informações pré-definidas.
## AllMiniLmL6V2EmbeddingModel
* Embed os segmentos utilizando o AllMiniLmL6V2EmbeddingModel
## InMemoryEmbeddingStore
* Armazene os embeddings em uma instância do InMemoryEmbeddingStore.
## EmbeddingStoreContentRetriever
* Defina uma ContentRetriever que busca respostas no banco de dados de embeddings.

[^1]: Arquivo Main.java importa várias bibliotecas relacionadas ao LangChain4j, incluindo DocumentParser, DocumentSplitter, EmbeddingModel e ContentRetriever.




