# Custom MCP Weather Server

This project is a Spring Boot application that acts as a Spring AI Model Context Protocol (MCP) server. It exposes a tool (function) that allows AI models to retrieve the current average temperature for a specified city.

The server uses external APIs to achieve this:
1.  **Geolocation:** [OpenStreetMap Nominatim](https://nominatim.openstreetmap.org/) is used to convert a city name into geographical coordinates (latitude and longitude).
2.  **Weather Data:** [Open-Meteo API](https://open-meteo.com/) is used to fetch the daily average temperature based on the obtained coordinates.

## Features

*   Provides a Spring AI MCP endpoint.
*   Exposes a `getTemperatureByCityName` tool for AI function calling.
*   Integrates with external Geolocation and Weather APIs.
*   Built with Spring Boot 3 and Java 21.

## Technology Stack

*   Java 21
*   Spring Boot 3.4.5
*   Spring AI 1.0.0-M7 (`spring-ai-starter-mcp-server`)
*   Gradle 8.13
*   RestClient (for external API calls)
*   OpenStreetMap Nominatim API
*   Open-Meteo API

## Prerequisites

*   JDK 21 or later (Ensure `JAVA_HOME` is set correctly)
*   Internet connection (for downloading dependencies and calling external APIs)
*   Gradle (Optional - the project includes a Gradle wrapper `./gradlew`)

## Building the Project

You can build the project and create an executable JAR file using the Gradle wrapper:

*   On Linux/macOS:
    ```bash
    ./gradlew build
    ```
*   On Windows:
    ```bash
    .\gradlew.bat build
    ```
The built JAR file will be located in the `build/libs/` directory (e.g., `build/libs/custom-mcp-weather-server-0.0.1.jar`).

## Running the Project

There are multiple ways to run the application:

1.  **Using the Gradle Wrapper:**
    *   On Linux/macOS:
        ```bash
        ./gradlew bootRun
        ```
    *   On Windows:
        ```bash
        .\gradlew.bat bootRun
        ```

2.  **Using the Executable JAR:**
    After building the project (see above), run the JAR file:
    ```bash
    java -jar build/libs/custom-mcp-weather-server-0.0.1.jar
    ```

## Running in STDIO Mode

the **custom-mcp-weather-server** can **ONLY** be run in STDIO (Standard Input/Output) mode, where it communicates directly via `stdin` and `stdout` instead of operating as a web server. This is often used when the server is launched as a subprocess by an AI client application.

To enable STDIO mode, the following Spring Boot properties **must** be set when launching the application (e.g., via command-line arguments or an `application.properties` file):

*   **`spring.main.web-application-type=none`**: Disables the embedded web server (like Tomcat). Communication will occur over STDIO, not HTTP.
*   **`spring.main.banner-mode=off`**: Turns off the Spring Boot startup banner. Any extraneous output to `stdout`, like the banner, would corrupt the MCP protocol stream.
*   **`logging.pattern.console=`**: Clears the default console logging pattern. Formatted log messages sent to `stdout` would also interfere with the MCP communication. This effectively silences standard console logging, ensuring `stdout` is used exclusively for the MCP protocol.

**Example command-line launch for STDIO mode:**

```bash
java -Dspring.main.web-application-type=none \
     -Dspring.main.banner-mode=off \
     -Dlogging.pattern.console= \
     -jar build/libs/custom-mcp-weather-server-0.0.1.jar
```

The server will start, typically on port 8080 (or as configured by Spring Boot), and will be ready to serve MCP requests.

## How it Works

1.  The application starts as a standard Spring Boot web server.
2.  It initializes the `WeatherService`, which contains the logic for fetching weather data.
3.  A `ToolCallbackProvider` bean is configured using `MethodToolCallbackProvider`, exposing methods annotated with `@Tool` from the `WeatherService` (specifically `getTemperatureByCityName`).
4.  The `spring-ai-starter-mcp-server` dependency handles the MCP protocol details, making the exposed tools available to compatible AI clients (like those built with LangChain4j or Spring AI).
5.  When an AI client invokes the `getTemperatureByCityName` tool via MCP:
    *   The `WeatherService` receives the city name.
    *   It calls the Nominatim API to get the latitude and longitude for the city.
    *   It calls the Open-Meteo API using these coordinates to get the daily average temperature.
    *   It returns the temperature as a string (e.g., "25.0 Graus Cesius") back to the AI client through the MCP mechanism.

## Notes

*   The external API calls include a `User-Agent` header. Ensure this is acceptable according to the terms of service of Nominatim and Open-Meteo, especially for high-volume usage.
*   Error handling for API calls (e.g., city not found, API unavailable) is basic.
*   The timezone for the weather query is currently hardcoded to "America/Sao_Paulo".

