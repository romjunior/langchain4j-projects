package com.estudo;

import java.util.Scanner;

public class Chatbot {
    public static void main(String[] args) {
        final var baseUrl = System.getenv("BASE_URL");
        final var model = System.getenv("MODEL_NAME");

        final var scanner = new Scanner(System.in);

        String input = null;

        final var assistant = Assistant.create(baseUrl, model);

        System.out.println("=======ISAC==========");

        do {
            System.out.print("[user]:");
            input = scanner.nextLine();
            if(!input.equals("/sair")) {
                final var response = assistant.chat(input);
                System.out.printf("[token] %s\n", response.tokenUsage());
                System.out.println("[ISAC]: " + response.content().text());
            }
        } while (!input.equals("/sair"));
    }
}