package org.todaybook.bookservice.application.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.todaybook.bookservice.application.dto.BookMapper;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.BookId;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;
import org.todaybook.bookservice.domain.service.BookManageService;
import org.todaybook.bookservice.domain.service.BookQueryService;
import org.todaybook.bookservice.domain.service.BookRegisterService;
import org.todaybook.bookservice.presentation.dto.BookRegisterRequest;
import org.todaybook.bookservice.presentation.dto.BookResponse;
import org.todaybook.bookservice.presentation.dto.BookUpdateRequest;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookQueryService queryService;
  private final BookManageService manageService;
  private final BookRegisterService registerService;

  @Override
  public List<BookResponse> getBooksByIds(List<UUID> ids) {
    List<BookId> bookIds = ids.stream().map(BookId::of).toList();
    List<Book> books = queryService.getBooksByIds(bookIds);

    return books.stream().map(BookResponse::from).toList();
  }

  @Override
  public void create(BookRegisterRequest request) {
    manageService.save(BookMapper.toDomain(request));
  }

  @Override
  public void update(UUID bookId, BookUpdateRequest request) {
    manageService.update(BookId.of(bookId), BookMapper.toDomain(request));
  }

  @Override
  public void register(List<BookRegisterRequest> request) {
    List<BookCreateInfo> bookCreateInfos = request.stream().map(BookMapper::toDomain).toList();

    registerService.register(bookCreateInfos);
  }
}
