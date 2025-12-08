package org.todaybook.bookservice.infrastructure.kfaka.dto;

import java.time.LocalDate;
import java.util.List;

public record BookConsumeMessage(
    String isbn,
    String title,
    List<String> categories,
    String description,
    String author,
    String publisher,
    LocalDate publishedAt,
    String thumbnail) {}
