package com.estudo.db;

import java.time.LocalDateTime;
import java.util.UUID;

public record Chat(
        UUID chatId,
        String content,
        LocalDateTime createdAt
) {
}
