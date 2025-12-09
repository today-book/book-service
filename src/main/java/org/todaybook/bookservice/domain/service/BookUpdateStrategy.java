package org.todaybook.bookservice.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;
import org.todaybook.bookservice.domain.dto.BookUpdateInfo;

@Slf4j
@Component
public class BookUpdateStrategy {

  public List<String> updateCategories(List<String> base, List<String> request) {
    if (base == null) return request == null ? List.of() : request;

    Set<String> categories = new HashSet<>(base);
    if (request != null) categories.addAll(request);

    return new ArrayList<>(categories);
  }

  public String updateDescription(String base, String request) {
    if (request == null) return base;
    return request.length() > base.length() ? request : base;
  }

  public String updatePublisher(String base, String request) {
    return (request != null && !request.isBlank()) ? request : base;
  }

  public LocalDate updatePublishedAt(LocalDate base, LocalDate request) {
    return (request != null) ? request : base;
  }

  public String updateThumbnail(String base, String request) {
    return (request != null && !request.isBlank()) ? request : base;
  }

  public BookUpdateInfo buildUpdateInfo(Book base, BookCreateInfo request) {
    List<String> categories = updateCategories(base.getCategories(), request.categories());
    String description = updateDescription(base.getDescription(), request.description());
    String publisher = updatePublisher(base.getPublisher(), request.publisher());
    LocalDate publishedAt = updatePublishedAt(base.getPublishedAt(), request.publishedAt());
    String thumbnail = updateThumbnail(base.getThumbnail(), request.thumbnail());

    return BookUpdateInfo.builder()
        .title(request.title())
        .categories(new ArrayList<>(categories))
        .description(description)
        .author(request.author())
        .publisher(publisher)
        .publishedAt(publishedAt)
        .thumbnail(thumbnail)
        .build();
  }
}
