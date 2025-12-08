package org.todaybook.bookservice.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.BookId;
import org.todaybook.bookservice.domain.repository.BookRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookQueryServiceImpl implements BookQueryService {

  private final BookRepository repository;

  @Override
  public List<Book> getBooksByIds(List<BookId> ids) {
    List<Book> books = repository.findAllById(ids);

    List<BookId> missingIds =
        ids.stream().filter(id -> books.stream().noneMatch(b -> b.getId().equals(id))).toList();

    if (!missingIds.isEmpty()) {
      log.debug("[BOOK-SERVICE] 찾을 수 없는 도서 ID: {}", missingIds);
    }

    return books;
  }
}
