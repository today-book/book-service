package org.todaybook.bookservice.domain.service;

import java.util.List;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;

public interface BookRegisterService {
  List<Book> register(List<BookCreateInfo> request);
}
