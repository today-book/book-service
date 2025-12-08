package org.todaybook.bookservice.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.BookId;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;
import org.todaybook.bookservice.domain.dto.BookUpdateInfo;
import org.todaybook.bookservice.domain.exception.BookAlreadyExistsException;
import org.todaybook.bookservice.domain.exception.BookNotFoundException;
import org.todaybook.bookservice.domain.repository.BookRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookManageServiceImpl implements BookManageService {

  private final BookRepository repository;

  @Override
  public Book save(BookCreateInfo request) {
    repository
        .findByIsbn(request.isbn())
        .ifPresent(
            book -> {
              throw new BookAlreadyExistsException(book.getIsbn());
            });

    return repository.save(Book.create(request));
  }

  @Override
  public Book update(BookId id, BookUpdateInfo request) {
    Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

    return repository.save(book.update(request));
  }
}
