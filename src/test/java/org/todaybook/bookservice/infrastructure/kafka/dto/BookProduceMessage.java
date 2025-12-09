package org.todaybook.bookservice.infrastructure.kafka.dto;

import java.time.LocalDate;
import java.util.List;

public record BookProduceMessage(
    String isbn,
    String title,
    List<String> categories,
    String description,
    String author,
    String publisher,
    LocalDate publishedAt,
    String thumbnail) {}
