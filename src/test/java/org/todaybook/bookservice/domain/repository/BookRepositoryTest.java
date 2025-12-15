package org.todaybook.bookservice.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.todaybook.bookservice.BookFixture;
import org.todaybook.bookservice.config.KafkaContainerConfig;
import org.todaybook.bookservice.config.PostgresContainerConfig;
import org.todaybook.bookservice.domain.Book;

@SpringBootTest
@ActiveProfiles("test")
@Import({PostgresContainerConfig.class})
class BookRepositoryTest extends KafkaContainerConfig {

  @Autowired private BookRepository repository;

  private Book book;

  @BeforeEach
  void setup() {
    book = Book.create(BookFixture.bookCreate());
    repository.save(book);
  }

  @Test
  @DisplayName("id로 조회 테스트")
  void test1() {
    Optional<Book> result = repository.findById(book.getId());

    assertTrue(result.isPresent());
    assertEquals(book.getIsbn(), result.get().getIsbn());

    System.out.println("result: " + result);
  }

  @Test
  @DisplayName("ids로 조회 테스트")
  void test2() {
    List<Book> result = repository.findByIds(List.of(book.getId()));

    assertEquals(1, result.size());

    System.out.println("result: " + result);
  }
}
