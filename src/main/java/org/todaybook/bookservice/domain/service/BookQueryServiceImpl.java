package org.todaybook.bookservice.domain.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.BookId;
import org.todaybook.bookservice.domain.repository.BookRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookQueryServiceImpl implements BookQueryService {

  private final BookRepository repository;

  @Override
  public List<Book> getBooksByIds(List<BookId> ids) {
    return repository.findAllById(ids);
  }
}
