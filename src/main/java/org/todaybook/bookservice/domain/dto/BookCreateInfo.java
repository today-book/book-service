package org.todaybook.bookservice.domain.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record BookCreateInfo(
    String isbn,
    String title,
    List<String> categories,
    String description,
    String author,
    String publisher,
    LocalDate publishedAt,
    String thumbnail) {}
