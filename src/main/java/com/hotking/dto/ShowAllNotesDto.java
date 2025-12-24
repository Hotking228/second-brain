package com.hotking.dto;


import java.time.Instant;

public record ShowAllNotesDto(
        Integer id,
        String title,
        Instant createdAt
) {
}
