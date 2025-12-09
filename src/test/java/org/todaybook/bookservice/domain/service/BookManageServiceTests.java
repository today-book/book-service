package org.todaybook.bookservice.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.todaybook.bookservice.BookFixture;
import org.todaybook.bookservice.config.TestContainersConfig;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.BookId;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;
import org.todaybook.bookservice.domain.dto.BookUpdateInfo;
import org.todaybook.bookservice.domain.exception.BookAlreadyExistsException;
import org.todaybook.bookservice.domain.exception.BookNotFoundException;
import org.todaybook.bookservice.domain.repository.BookRepository;
import org.todaybook.commoncore.message.MessageResolver;

@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:sql/book-data.sql")
@Import({TestContainersConfig.class})
class BookManageServiceTests {

  @Autowired private BookRepository repository;

  @Autowired private BookManageService bookManageService;

  @Autowired private MessageResolver messageResolver;

  @Test
  @DisplayName("도서 저장 테스트")
  void test1() {
    BookCreateInfo createInfo = BookFixture.bookCreate();

    Book saved = bookManageService.save(createInfo);

    Book found =
        repository
            .findById(saved.getId())
            .orElseThrow(() -> new AssertionError("요청하신 도서를 찾을 수 없습니다"));

    assertEquals(createInfo.isbn(), found.getIsbn());
  }

  @Test
  @DisplayName("도서 중복 저장 테스트")
  void test2() {
    BookCreateInfo createInfo = BookFixture.bookCreate("978000000001");

    BookAlreadyExistsException exception =
        assertThrows(BookAlreadyExistsException.class, () -> bookManageService.save(createInfo));

    System.out.println(
        "message: " + messageResolver.resolve(exception.getCode(), exception.getErrorArgs()));
  }

  @Test
  @DisplayName("도서 수정 테스트")
  void test3() {
    BookUpdateInfo bookUpdateInfo = BookFixture.bookUpdate();

    BookId bookId = BookId.of(UUID.fromString("11111111-1111-1111-1111-111111111111"));
    Book updated = bookManageService.update(bookId, bookUpdateInfo);

    Book found =
        repository
            .findById(updated.getId())
            .orElseThrow(() -> new AssertionError("요청하신 도서를 찾을 수 없습니다"));

    assertEquals(bookUpdateInfo.title(), found.getTitle());
  }

  @Test
  @DisplayName("잘못된 bookId로 도서 수정 테스트")
  void test4() {
    BookUpdateInfo bookUpdateInfo = BookFixture.bookUpdate();

    BookId bookId = BookId.of(UUID.fromString("00000000-0000-0000-0000-000000000000"));

    BookNotFoundException exception =
        assertThrows(
            BookNotFoundException.class, () -> bookManageService.update(bookId, bookUpdateInfo));

    System.out.println(
        "message: " + messageResolver.resolve(exception.getCode(), exception.getErrorArgs()));
  }
}
