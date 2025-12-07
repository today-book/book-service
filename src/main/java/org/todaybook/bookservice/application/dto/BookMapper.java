package org.todaybook.bookservice.application.dto;

import org.springframework.stereotype.Component;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;
import org.todaybook.bookservice.domain.dto.BookUpdateInfo;
import org.todaybook.bookservice.infrastructure.kfaka.dto.BookConsumeMessage;
import org.todaybook.bookservice.presentation.dto.BookRegisterRequest;
import org.todaybook.bookservice.presentation.dto.BookUpdateRequest;

@Component
public class BookMapper {
  public static BookCreateInfo toDomain(BookRegisterRequest request) {
    return new BookCreateInfo(
        request.getIsbn(),
        request.getTitle(),
        request.getCategories(),
        request.getDescription(),
        request.getAuthor(),
        request.getPublisher(),
        request.getPublishedAt(),
        request.getThumbnail());
  }

  public static BookUpdateInfo toDomain(BookUpdateRequest request) {
    return new BookUpdateInfo(
        request.getTitle(),
        request.getCategories(),
        request.getDescription(),
        request.getAuthor(),
        request.getPublisher(),
        request.getPublishedAt(),
        request.getThumbnail());
  }

  public static BookRegisterRequest toRequest(BookConsumeMessage request) {
    return new BookRegisterRequest(
        request.isbn(),
        request.title(),
        request.categories(),
        request.description(),
        request.author(),
        request.publisher(),
        request.publishedAt(),
        request.thumbnail());
  }
}
