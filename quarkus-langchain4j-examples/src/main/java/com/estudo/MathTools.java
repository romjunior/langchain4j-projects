package com.estudo;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MathTools {

    @Tool("Returns the result of subtracting the second number from the first")
    public int sum(int a, int b) {
        return a + b;
    }

    @Tool("Returns the result of subtracting the second number from the first")
    public int subtract(int a, int b) {
        return a - b;
    }

    @Tool("Returns the result of multiplying the two numbers")
    public int multiply(int a, int b) {
        return a * b;
    }

    @Tool("Returns the result of dividing the first number by the second")
    public int divide(int a, int b) {
        return a / b;
    }
}
