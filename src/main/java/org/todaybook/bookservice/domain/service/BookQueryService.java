package org.todaybook.bookservice.domain.service;

import java.util.List;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.BookId;

public interface BookQueryService {
  List<Book> getBooksByIds(List<BookId> ids);
}
