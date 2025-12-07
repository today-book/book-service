package org.todaybook.bookservice.application.service;

import java.util.List;
import java.util.UUID;
import org.todaybook.bookservice.domain.BookId;
import org.todaybook.bookservice.presentation.dto.BookRegisterRequest;
import org.todaybook.bookservice.presentation.dto.BookResponse;
import org.todaybook.bookservice.presentation.dto.BookUpdateRequest;

public interface BookService {
  List<BookResponse> getBooksByIds(List<BookId> ids);

  void create(BookRegisterRequest request);

  void update(UUID bookId, BookUpdateRequest request);

  void register(List<BookRegisterRequest> request);
}
