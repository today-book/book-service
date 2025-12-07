package org.todaybook.bookservice.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.todaybook.bookservice.domain.Book;

public record BookResponse(
    UUID id,
    String isbn,
    String title,
    List<String> categories,
    String description,
    String author,
    String publisher,
    LocalDateTime publishedAt,
    String thumbnail,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
  public static BookResponse from(Book book) {
    return new BookResponse(
        book.getId().toUUID(),
        book.getIsbn(),
        book.getTitle(),
        book.getCategories(),
        book.getDescription(),
        book.getAuthor(),
        book.getPublisher(),
        book.getPublishedAt(),
        book.getThumbnail(),
        book.getCreatedAt(),
        book.getUpdatedAt());
  }
}
