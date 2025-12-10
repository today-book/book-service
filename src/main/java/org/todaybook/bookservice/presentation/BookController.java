package org.todaybook.bookservice.presentation;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.todaybook.bookservice.application.service.BookService;
import org.todaybook.bookservice.presentation.dto.BookListResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/books")
public class BookController {

  private final BookService bookService;

  @PostMapping("/ids")
  public BookListResponse getBooksByIds(@RequestBody List<UUID> ids) {
    return bookService.getBooksByIds(ids);
  }
}
