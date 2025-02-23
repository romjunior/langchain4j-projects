# Parking Lot

Este repositório contém uma solução completa para gerenciamento e interação de usuários com um chatbot especializado em estacionamento. Todos os projetos utilizam **Quarkus** e **Langchain4j-Quarkus** a ideia é construir POCs com IA executando localmente.

---
## Diagrama de Arquitetura

Abaixo está o diagrama que representa a arquitetura geral do sistema:

![Arquitetura do Projeto](./architecture.svg)

## Componentes Principais

1. **parkingbr-web**  
   - **Descrição:** Front-end web responsável pela interação direta com o usuário.  
   - **Tecnologias:**  
     - Quarkus (para possíveis rotas e serviços que expõe se necessário)  
     - Comunicação via WebSocket com o serviço **message-in**  
     - Comunicação via REST (API) com o **parkingbr-chatbot-core**

2. **message-in**  
   - **Descrição:** Serviço responsável por receber mensagens via WebSocket e enviá-las ao **parkingbr-chatbot-core**.  
   - **Tecnologias:**  
     - Quarkus  
     - Filas e mensageria usando AMQP

3. **parkingbr-chatbot-core**  
   - **Descrição:** Núcleo do chatbot, onde reside a lógica de conversação, gerenciamento de contexto e orquestração de chamadas para o **parkingbr-integration-api**.  
   - **Tecnologias:**  
     - Quarkus  
     - **Langchain4j-Quarkus** para interação com modelos de linguagem (LLM)  
     - Acesso ao **Banco** (armazenamento de dados)  
     - Acesso ao **Cache** para melhorar performance  
     - Recebe mensagens de **message-in** (AMQP) e envia para **message-out** (AMQP)

4. **message-out**  
   - **Descrição:** Serviço que trata as mensagens de saída do **parkingbr-chatbot-core** e direciona-as de volta ao **parkingbr-web** (ou outros destinos).  
   - **Tecnologias:**  
     - Quarkus  
     - Filas e mensageria usando AMQP

5. **parkingbr-integration-api**  
   - **Descrição:** API de integração responsável por se comunicar com sistemas externos ou internos, bem como com o LLM.  
   - **Tecnologias:**  
     - Quarkus  
     - Comunicação REST com **parkingbr-chatbot-core**  
     - Acesso ao **Banco** próprio para persistência de dados específicos  
     - Integração com **LLM**

6. **LLM**  
   - **Descrição:** Modelo de Linguagem (Large Language Model) que processa e gera respostas complexas ou contexto adicional para o chatbot.  
   - **Integração:** Utilizado principalmente pelo **parkingbr-integration-api** através de **Langchain4j-Quarkus**.

7. **Banco**  
   - **Descrição:** Base de dados onde são armazenadas informações da aplicação (usuários, sessões, histórico de conversas, etc.).  
   - Podem existir instâncias de banco distintas, conforme indicado no diagrama.

8. **Cache**  
   - **Descrição:** Camada de cache (pode ser Redis ou outra solução) para melhorar a performance do **parkingbr-chatbot-core** em consultas frequentes.

---

## Fluxo de Comunicação

1. O usuário acessa o **parkingbr-web** e inicia uma conversa (via WebSocket ou via API).
2. Se a mensagem for via WebSocket, o **parkingbr-web** envia a mensagem para o **message-in**.
3. O **message-in** publica a mensagem em uma fila (AMQP) para o **parkingbr-chatbot-core**.
4. O **parkingbr-chatbot-core** processa a mensagem, acessa o **Cache** e o **Banco** se necessário, e pode fazer chamadas REST para o **parkingbr-integration-api**.
5. O **parkingbr-integration-api** pode acessar seu próprio **Banco** ou se comunicar com o **LLM** para enriquecer a resposta.
6. A resposta final do **parkingbr-chatbot-core** segue para o **message-out** via AMQP.
7. O **message-out** então envia a mensagem de volta ao front-end ou a outros destinos configurados.

---

## Tecnologias Utilizadas

- **Quarkus**: Framework Java de alta performance, com inicialização rápida e baixo uso de memória.
- **Langchain4j-Quarkus**: Biblioteca que integra fluxos de conversação e LLMs dentro de aplicações Quarkus, facilitando o uso de modelos de linguagem.

## Projeto Pausado

Por questões da LLM Local Alucinar muito, eu vou pausar esse projeto. Ele está funcional mas dependendo do Modelo executado local pode ter mais ou menos alucinações.