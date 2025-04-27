# Langchain4j MCP Examples (Filesystem & Browser)

## Introdução

Este projeto demonstra como integrar o framework [Langchain4j](https://github.com/langchain4j/langchain4j) com ferramentas externas utilizando o **Model Context Protocol (MCP)**. Ele fornece exemplos práticos que permitem a um modelo de linguagem (LLM), servido localmente via [Ollama](https://ollama.com/), interagir com:

1.  **O sistema de arquivos local:** Listar arquivos em um diretório especificado (`FileSystemExample.java`).
2.  **Um navegador web:** Acessar uma URL, extrair informações do HTML (`BrowseMCPExample.java`).

O projeto utiliza `StdioMcpTransport` para iniciar e comunicar-se com servidores MCP externos (executados via `npx`) que expõem as funcionalidades de sistema de arquivos e navegador como ferramentas para o Langchain4j.

## Stack Tecnológica

*   **Linguagem:** Java (JDK)
*   **Build Tool:** Gradle
*   **Framework IA:** Langchain4j
    *   `langchain4j-core`: Funcionalidades centrais.
    *   `langchain4j-ollama`: Integração com o servidor Ollama.
    *   `langchain4j-mcp`: Cliente e ferramentas para Model Context Protocol.
*   **Protocolo de Ferramentas:** Model Context Protocol (MCP)
    *   Servidor MCP para Filesystem: `@modelcontextprotocol/server-filesystem`
    *   Servidor MCP para Browser: `@browsermcp/mcp`
*   **LLM Provider:** Ollama (executando localmente)
*   **Runtime para Servidores MCP:** Node.js / npm / npx
*   **Gerenciador de Versões (utilizado nos exemplos):** asdf (para `npx`)
*   **Logging:** SLF4j com binding para Reload4j (configurado via `log4j.properties`)

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

1.  **JDK:** Java Development Kit (ex: versão 17 ou superior).
2.  **Gradle:** Ou utilize o Gradle Wrapper (`./gradlew`) incluído no projeto.
3.  **Node.js e npm/npx:** Necessário para executar os servidores MCP.
4.  **asdf (Opcional, mas usado nos exemplos):** Se você não usa `asdf`, precisará ajustar os caminhos para `npx` nas classes `FileSystemExample.java` e `BrowseMCPExample.java` para o caminho correto do seu executável `npx`.
5.  **Ollama:** Instale e execute o Ollama localmente.
    *   Verifique se ele está acessível no endereço padrão `http://localhost:11434`.
    *   Baixe o modelo LLM que deseja usar (ex: `ollama pull llama3.2`).

## Configuração

1.  **Clone o Repositório:** Esse aqui
2. **crie o arquivo .env:** Esse arquivo é para você utilizar para execução via IDE se precisar
   * BASE_URL: url do ollama
     MODEL_NAME: nome do modelo(no exemplo foi usado o llama3.2)
     EXAMPLE_PATH: caminho que o Servidor de MCP para Filesystem vai usar para iniciar como base
## Links Úteis
* BrowserMCP: https://browsermcp.io/ - Documentação e informações sobre o servidor MCP para interação com navegador.
* Langchain4j MCP Tutorial: https://docs.langchain4j.dev/tutorials/mcp/ - Tutorial oficial do Langchain4j sobre o uso do Model Context Protocol.