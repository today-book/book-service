package org.todaybook.bookservice.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
import org.todaybook.bookservice.domain.dto.BookCreateInfo;

@SpringBootTest
@ActiveProfiles("test")
@Import({PostgresContainerConfig.class})
class BookRegisterServiceTests extends KafkaContainerConfig {

  @Autowired private BookRegisterService bookRegisterService;

  @Test
  @DisplayName("도서 등록 테스트")
  void test1() {
    List<BookCreateInfo> bookCreateInfos =
        IntStream.range(0, 10)
            .mapToObj(i -> BookFixture.bookCreate("000000000000" + i))
            .collect(Collectors.toList());
    bookCreateInfos.add(BookFixture.bookCreate("0000000000000"));

    List<Book> books = bookRegisterService.register(bookCreateInfos);

    assertEquals(10, books.size());
  }
}
