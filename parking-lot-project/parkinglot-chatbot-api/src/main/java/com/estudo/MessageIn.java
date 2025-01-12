package com.estudo;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record MessageIn(String memoryId, String message) {
}
