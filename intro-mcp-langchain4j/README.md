# Langchain4j MCP Examples (Filesystem, Browser & Custom)

## Introdução

Este projeto demonstra como integrar o framework [Langchain4j](https://github.com/langchain4j/langchain4j) com ferramentas externas utilizando o **Model Context Protocol (MCP)**. Ele fornece exemplos práticos que permitem a um modelo de linguagem (LLM), servido localmente via [Ollama](https://ollama.com/), interagir com:

1.  **O sistema de arquivos local:** Listar arquivos em um diretório especificado (`FileSystemExample.java`).
2.  **Um navegador web:** Acessar uma URL, extrair informações do HTML (`BrowseMCPExample.java`).
3.  **Um servidor MCP(STDIO) customizado:** Obter a temperatura de uma cidade utilizando um servidor MCP implementado em Java e executado como um processo separado via comunicação Standard Input/Output (`CustomMCPWeatherCityExample.java`).
4.  **Um servidor MCP(SSE) customizado:** Obter a temperatura de uma cidade utilizando um servidor MCP implementado em Java e comunicado via Server-Sent Events (SSE), executado através de um *runner* específico (`SseMCPWeatherCityExample.java`).

O projeto utiliza transportes como `StdioMcpTransport` e comunicação baseada em SSE para iniciar e comunicar-se com servidores MCP externos (executados via `npx` para os exemplos de filesystem/browser, via `java -jar` para o exemplo customizado STDIO, e via um executável *runner* para o exemplo customizado SSE) que expõem as funcionalidades como ferramentas para o Langchain4j.

## Stack Tecnológica

*   **Linguagem:** Java (JDK)
*   **Build Tool:** Gradle
*   **Framework IA:** Langchain4j
    *   `langchain4j-core`: Funcionalidades centrais.
    *   `langchain4j-ollama`: Integração com o servidor Ollama.
    *   `langchain4j-mcp`: Cliente e ferramentas para Model Context Protocol.
*   **Protocolo de Ferramentas:** Model Context Protocol (MCP)
    *   Servidor MCP para Filesystem: `@modelcontextprotocol/server-filesystem` (via npx)
    *   Servidor MCP para Browser: `@browsermcp/mcp` (via npx)
    *   Servidor MCP Customizado para Clima (STDIO): `custom-mcp-weather-server-0.0.1.jar` (via java -jar)
    *   Servidor MCP Customizado para Clima (SSE): Executado via um *runner* específico que gerencia o processo do servidor (detalhes no `SseMCPWeatherCityExample.java`).
*   **LLM Provider:** Ollama (executando localmente)
*   **Runtime para Servidores MCP:** Node.js / npm / npx (para exemplos de filesystem/browser), JVM (para exemplo de clima STDIO e para o *runner* do exemplo de clima SSE)
*   **Gerenciador de Versões (utilizado nos exemplos):** asdf (para `npx` e `java`)
*   **Logging:** SLF4j com binding para Reload4j (configurado via `log4j.properties`)

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

1.  **JDK:** Java Development Kit (ex: versão 17 ou superior).
2.  **Gradle:** Ou utilize o Gradle Wrapper (`./gradlew`) incluído no projeto.
3.  **Node.js e npm/npx:** Necessário para executar os servidores MCP dos exemplos de Filesystem e Browser.
4.  **asdf (Opcional, mas usado nos exemplos):** Se você não usa `asdf`, precisará ajustar os caminhos para `npx` nas classes `FileSystemExample.java` e `BrowseMCPExample.java`, o caminho para `java` (ou o comando `asdf exec java`) na classe `CustomMCPWeatherCityExample.java`, e o caminho para o executável *runner* na classe `SseMCPWeatherCityExample.java` para os caminhos corretos dos seus executáveis.
5.  **Ollama:** Instale e execute o Ollama localmente.
    *   Verifique se ele está acessível no endereço padrão `http://localhost:11434`.
    *   Baixe o modelo LLM que deseja usar (ex: `ollama pull llama3.2`).
6.  **Servidor MCP Customizado (para `CustomMCPWeatherCityExample`):** O arquivo `custom-mcp-weather-server-0.0.1.jar` deve estar presente no diretório raiz do projeto (ou o caminho no código `CustomMCPWeatherCityExample.java` deve ser ajustado). Este JAR é o servidor MCP que fornece a ferramenta de clima via STDIO. *Nota: Este JAR não está incluído neste repositório e precisaria ser construído ou obtido separadamente.*
7.  **Runner do Servidor MCP SSE (para `SseMCPWeatherCityExample`):** O executável *runner* responsável por iniciar o servidor MCP de clima para comunicação via SSE deve estar presente e acessível no caminho configurado em `SseMCPWeatherCityExample.java`. Este *quarkus-custom-mcp-sse-0.0.1-runner* gerencia o ciclo de vida do servidor MCP real.
## Configuração

1.  **Clone o Repositório:** Esse aqui
2.  **Construa o Projeto (se necessário):** `./gradlew build` (pode baixar dependências)
3.  **Crie o arquivo .env:** Esse arquivo é para você utilizar para execução via IDE se precisar.
    *   `BASE_URL`: url do ollama (ex: `http://localhost:11434`)
    *   `MODEL_NAME`: nome do modelo (ex: `llama3.2`)
    *   `EXAMPLE_PATH`: caminho que o Servidor de MCP para Filesystem vai usar para iniciar como base (ex: `/home/user/Documents`)
4.  **Ajuste os Caminhos dos Executáveis (se não usar `asdf`):** Conforme mencionado nos pré-requisitos, edite os caminhos dos comandos `npx`, `java -jar` e do *runner* do servidor SSE dentro dos arquivos Java de exemplo (`FileSystemExample.java`, `BrowseMCPExample.java`, `CustomMCPWeatherCityExample.java`, `SseMCPWeatherCityExample.java`) se você não estiver usando a estrutura de diretórios do `asdf` mostrada nos exemplos.
5.  **Disponibilize o JAR do Servidor de Clima (STDIO):** Certifique-se de que o `custom-mcp-weather-server-0.0.1.jar` esteja no local correto para que o `CustomMCPWeatherCityExample` possa executá-lo.
6.  **Disponibilize o Runner do Servidor SSE:** Certifique-se de executar o executável *quarkus-custom-mcp-sse-0.0.1-runner* para o servidor de clima SSE para que o `SseMCPWeatherCityExample` possa executá-lo.

## Executando os Exemplos

Você pode executar as classes `main` diretamente pela sua IDE (certifique-se de que as variáveis de ambiente do `.env` estejam carregadas) ou via Gradle:

*   `./gradlew runFileSystemExample`
*   `./gradlew runBrowseMCPExample`
*   `./gradlew runCustomMCPWeatherCityExample` (Requer que o `custom-mcp-weather-server-0.0.1.jar` esteja presente)
*   `./gradlew runSseMCPWeatherCityExample` (Requer que o *runner* do servidor MCP SSE esteja presente e configurado)

## Links Úteis
* BrowserMCP: https://browsermcp.io/ - Documentação e informações sobre o servidor MCP para interação com navegador.
* Langchain4j MCP Tutorial: https://docs.langchain4j.dev/tutorials/mcp/ - Tutorial oficial do Langchain4j sobre o uso do Model Context Protocol.
