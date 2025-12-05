package org.todaybook.bookservice.domain.service;

import java.util.List;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.BookId;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;
import org.todaybook.bookservice.domain.dto.BookUpdateInfo;

public interface BookManageService {
  Book save(BookCreateInfo request);

  Book update(BookId id, BookUpdateInfo request);

  void register(List<BookCreateInfo> request);
}
