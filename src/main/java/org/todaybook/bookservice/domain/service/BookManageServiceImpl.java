package org.todaybook.bookservice.domain.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.BookId;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;
import org.todaybook.bookservice.domain.dto.BookUpdateInfo;
import org.todaybook.bookservice.domain.repository.BookRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookManageServiceImpl implements BookManageService {

  private final BookRepository repository;

  @Override
  public Book save(BookCreateInfo request) {
    Book book = Book.create(request);

    repository.save(book);

    return book;
  }

  @Override
  public Book update(BookId id, BookUpdateInfo request) {
    Book book = repository.findById(id).orElseThrow();

    book.update(request);

    repository.save(book);

    return book;
  }

  @Override
  public void register(List<BookCreateInfo> request) {
    // isbn 조회
    List<String> isbns = request.stream().map(BookCreateInfo::isbn).collect(Collectors.toList());

    // 도서 매핑 정보 생성
    Map<String, Book> books =
        repository.findByIsbns(isbns).stream()
            .collect(Collectors.toMap(Book::getIsbn, Function.identity()));

    // 실제로 저장할 도서 목록 생성
    List<Book> bookList = new ArrayList<>();
    for (BookCreateInfo dto : request) {
      try {
        if (books.containsKey(dto.isbn())) {
          Book book = books.get(dto.isbn());
          bookList.add(update(book, dto));
        } else {
          bookList.add(Book.create(dto));
        }
      } catch (Exception e) {
        log.warn("Failed to save book with ISBN: {}: {}", dto.isbn(), e.getMessage());
      }
    }

    repository.saveAll(bookList);
  }

  private Book update(Book book, BookCreateInfo request) {
    // 카테고리 업데이트
    Set<String> categories = new HashSet<>(book.getCategories());
    if (request.categories() != null) {
      categories.addAll(request.categories());
    }

    // 도서 소개 업데이트
    String description = request.description();
    if (request.description() == null || description.length() < book.getDescription().length()) {
      description = book.getDescription();
    }

    // 출판사, 출판일, 도서 이미지 업데이트
    String publisher = request.publisher() != null ? request.publisher() : book.getPublisher();
    LocalDateTime publishedAt =
        request.publishedAt() != null ? request.publishedAt() : book.getPublishedAt();
    String thumbnail = request.thumbnail() != null ? request.thumbnail() : book.getThumbnail();

    BookUpdateInfo bookUpdate =
        BookUpdateInfo.builder()
            .title(request.title())
            .categories(new ArrayList<>(categories))
            .description(description)
            .author(request.author())
            .publisher(publisher)
            .publishedAt(publishedAt)
            .thumbnail(thumbnail)
            .build();

    return book.update(bookUpdate);
  }
}
