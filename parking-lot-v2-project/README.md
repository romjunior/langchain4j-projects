# Parking Lot V2 Project - Mono-repo

Este é um mono-repo contendo dois projetos Quarkus que implementam um sistema inteligente de gerenciamento de estacionamento com integração de IA.

## Proposta do Projeto

Sistema inteligente de estacionamento com **atendente virtual** que utiliza IA para:
- **Alocar vagas** através de conversação natural
- **Consultar situação** de veículos estacionados
- **Processar pagamentos** de forma automatizada
- **Integrar sistemas** via Model Context Protocol (MCP)

### Funcionalidades Principais
- **Chat conversacional** com agente roteador inteligente
- **Alocação de vagas** com validação de placa e disponibilidade
- **Consulta de alocações** por placa do veículo
- **Cálculo e pagamento** automático baseado em tempo de permanência
- **Persistência em PostgreSQL** com dados estruturados
- **Memória de conversação** usando Redis

## Estrutura do Projeto

```
parking-lot-v2-project/
├── parking-lot/                 # Aplicação principal
│   ├── src/main/java/          # Código fonte Java
│   ├── src/main/resources/     # Recursos e configurações
│   └── build.gradle            # Dependências do projeto
├── parking-lot-mcp/            # Servidor MCP
│   ├── src/main/java/          # Implementação do servidor MCP
│   ├── src/main/resources/     # Configurações MCP
│   └── build.gradle            # Dependências MCP
├── gradle/                     # Wrapper do Gradle
├── build.gradle                # Configuração raiz
├── settings.gradle             # Configuração multi-projeto
└── README.md                   # Este arquivo
```

### parking-lot/ (Porta: 8080)
**Sistema de Chat com IA**
- **RouterAgent**: Classifica intenções e roteia para agentes especializados
- **AllocationAgent**: Gerencia processo completo de alocação de vagas
- **ConsultAgent**: Consulta situação de veículos por placa
- **PayAgent**: Calcula valores e processa pagamentos
- **Memória Redis**: Mantém contexto das conversações
- **Integração Ollama**: Usa modelo qwen2.5 para processamento de linguagem

### parking-lot-mcp/ (Porta: 8081)
**Servidor de Ferramentas MCP**
- **AllocationTool**: Criar e consultar alocações de vagas
- **ParkingSpaceTool**: Listar vagas disponíveis e validar placas
- **PaymentTool**: Calcular valores e processar pagamentos
- **PriceTool**: Consultar tabela de preços do estacionamento
- **PostgreSQL**: Persistência de dados estruturados

## Preparação do Ambiente

### Pré-requisitos
- **Java 21** ou superior
- **Ollama** instalado e rodando localmente
- **PostgreSQL** para o servidor MCP
- **Redis** para memória de conversação
- **Git** para clonar o repositório

### Configuração do Ollama
1. Instale o Ollama: https://ollama.ai/
2. Baixe o modelo qwen2.5:
   ```bash
   ollama pull qwen2.5
   ```
3. Verifique se está rodando:
   ```bash
   ollama list
   ```

### Configuração do PostgreSQL
1. Crie o banco de dados:
   ```sql
   CREATE DATABASE parkinglot2;
   CREATE USER parkinglot2 WITH PASSWORD 'parkinglot1232';
   GRANT ALL PRIVILEGES ON DATABASE parkinglot2 TO parkinglot2;
   ```
2. Execute o script de inicialização:
   ```bash
   psql -U parkinglot2 -d parkinglot2 -f parking-lot-mcp/init.sql
   ```

### Configuração do Redis
```bash
# Instalar e iniciar Redis
sudo apt install redis-server  # Ubuntu/Debian
brew install redis            # macOS
redis-server                  # Iniciar servidor
```

### Configuração do Projeto
1. Clone o repositório:
   ```bash
   git clone <url-do-repositorio>
   cd parking-lot-v2-project
   ```

2. Verifique a versão do Java:
   ```bash
   java -version
   ```

3. Torne o wrapper do Gradle executável (Linux/Mac):
   ```bash
   chmod +x gradlew
   ```

## Comandos de Execução

### Desenvolvimento

#### Executar todos os projetos simultaneamente
```bash
./gradlew quarkusDev
```
*Inicia ambos os projetos em modo de desenvolvimento com hot reload*

#### Executar projetos individualmente
```bash
# Apenas o sistema principal
./gradlew :parking-lot:quarkusDev

# Apenas o servidor MCP
./gradlew :parking-lot-mcp:quarkusDev
```

### Build e Testes

#### Build completo
```bash
./gradlew build
```

#### Build por projeto
```bash
./gradlew :parking-lot:build
./gradlew :parking-lot-mcp:build
```

#### Executar testes
```bash
./gradlew test
```

#### Limpar builds anteriores
```bash
./gradlew clean
```

### Utilitários

#### Listar todas as tarefas
```bash
./gradlew tasks
```

#### Verificar dependências
```bash
./gradlew dependencies
```

## Detalhes dos Projetos

### parking-lot (Porta: 8080)
**Atendente Virtual Conversacional**

**Fluxos de Conversação:**

**1. Alocação de Vaga:**
- Informa preços e solicita confirmação
- Lista vagas disponíveis para escolha
- Solicita placa (formato: ABC-1234) e cor do veículo
- Valida dados e confirma alocação

**2. Consulta de Situação:**
- Solicita placa do veículo
- Retorna: placa, cor, vaga, horário de entrada

**3. Pagamento:**
- Solicita placa do veículo
- Calcula valor baseado em tempo de permanência
- Solicita confirmação e processa pagamento

**Endpoint:**
- `POST /chat` - Interface de chat com o atendente virtual

**Formato da requisição:**
```json
{
  "memoryId": "user123",
  "message": "Quero estacionar meu carro"
}
```

**Acesso após iniciar:**
- Chat API: http://localhost:8080/chat
- Health Check: http://localhost:8080/q/health

### parking-lot-mcp (Porta: 8081)
**Servidor de Dados e Ferramentas**

**Ferramentas Disponíveis:**
- **Alocação**: Criar/consultar alocações por placa
- **Vagas**: Listar vagas disponíveis (A1, A2, A3, A4, A5)
- **Validação**: Verificar formato de placa (ABC-1234)
- **Pagamento**: Calcular valores e processar pagamentos
- **Preços**: Primeira hora R$5,00 / Demais horas R$3,00

**Banco de Dados:**
- **PARKING_SPACE**: Vagas e status
- **ALLOCATION**: Alocações de veículos
- **PAYMENT**: Histórico de pagamentos
- **PRICE**: Tabela de preços

## Stack Tecnológico

### Core
- **Quarkus 3.17.4** - Framework Java nativo para nuvem
- **Java 21** - Linguagem de programação com recursos modernos
- **Gradle** - Sistema de build multi-projeto

### Inteligência Artificial
- **LangChain4j** - Framework para integração com LLMs
- **Ollama qwen2.5** - Modelo de linguagem local
- **MCP (Model Context Protocol)** - Comunicação entre agentes e ferramentas
- **Agentes especializados** - RouterAgent, AllocationAgent, ConsultAgent, PayAgent

### Persistência
- **PostgreSQL** - Banco principal com dados estruturados
- **Redis** - Cache de memória para conversações
- **Hibernate ORM** - Mapeamento objeto-relacional

### Desenvolvimento
- **Quarkus Dev Mode** - Hot reload para desenvolvimento
- **RESTEasy** - Implementação JAX-RS
- **MCP Tools** - Ferramentas customizadas para IA

## Troubleshooting

### Problemas Comuns

**Erro de conexão com Ollama:**
```bash
# Verifique se o modelo qwen2.5 está disponível
ollama list

# Baixe o modelo se necessário
ollama pull qwen2.5

# Reinicie o Ollama
ollama serve
```

**Erro de conexão com PostgreSQL:**
```bash
# Verifique se o PostgreSQL está rodando
sudo systemctl status postgresql

# Teste a conexão
psql -U parkinglot2 -d parkinglot2 -h localhost
```

**Erro de conexão com Redis:**
```bash
# Verifique se o Redis está rodando
redis-cli ping

# Inicie o Redis se necessário
redis-server
```

**Porta já em uso:**
```bash
# Verifique processos usando as portas
lsof -i :8080
lsof -i :8081
lsof -i :5432  # PostgreSQL
lsof -i :6379  # Redis

# Mate o processo se necessário
kill -9 <PID>
```

### Logs Úteis
- Logs do parking-lot: Console do Quarkus
- Logs do parking-lot-mcp: `parkinglot-quarkus.log`
- Logs do Ollama: `ollama logs`
- Teste de chat: `curl -X POST http://localhost:8080/chat -H "Content-Type: application/json" -d '{"memoryId":"test","message":"Olá"}'`

## Exemplo de Uso

### Conversação de Alocação
```bash
# 1. Cumprimento inicial
curl -X POST http://localhost:8080/chat \
  -H "Content-Type: application/json" \
  -d '{"memoryId":"user1","message":"Olá"}'

# 2. Solicitar alocação
curl -X POST http://localhost:8080/chat \
  -H "Content-Type: application/json" \
  -d '{"memoryId":"user1","message":"Quero estacionar meu carro"}'

# 3. Confirmar preço e escolher vaga
# 4. Informar placa (ABC-1234) e cor
```

### Consulta de Situação
```bash
curl -X POST http://localhost:8080/chat \
  -H "Content-Type: application/json" \
  -d '{"memoryId":"user2","message":"Quero consultar meu carro de placa ABC-1234"}'
```

### Pagamento
```bash
curl -X POST http://localhost:8080/chat \
  -H "Content-Type: application/json" \
  -d '{"memoryId":"user3","message":"Quero pagar o estacionamento da placa ABC-1234"}'
```

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request