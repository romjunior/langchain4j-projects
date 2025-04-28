package com.estudo.custom_mcp_weather_server;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomMcpWeatherServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomMcpWeatherServerApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider weatherTools(WeatherService weatherService) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(weatherService)
				.build();
	}

	//pra teste precisa retirar o logging.pattern.console= do properties, não esqueça de colocar de volta pra prod
	/*@Bean
	public CommandLineRunner weatherTest(WeatherService weatherService) {
		return args -> System.out.println(weatherService.getTemperatureByCityName("São Paulo"));
	}*/
}
