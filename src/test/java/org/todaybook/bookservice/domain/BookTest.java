package org.todaybook.bookservice.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.todaybook.bookservice.BookFixture;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;
import org.todaybook.bookservice.domain.dto.BookUpdateInfo;

class BookTest {

  @Test
  @DisplayName("도서 생성 테스트")
  void test1() {
    Book result = Book.create(BookFixture.bookCreate());

    assertNotNull(result);
    assertEquals("000000000001", result.getIsbn());
  }

  @Test
  @DisplayName("잘못된 도서 생성 테스트")
  void test2() {
    BookCreateInfo dto =
        new BookCreateInfo(null, "도서 제목", List.of("소설"), "도서 설명", "도서 저자", null, null, null);

    assertThrows(IllegalArgumentException.class, () -> Book.create(dto));
  }

  @Test
  @DisplayName("도서 업데이트 테스트")
  void test3() {
    Book book = Book.create(BookFixture.bookCreate());

    Book result = book.update(BookFixture.bookUpdate());

    assertNotNull(result);
    assertEquals("도서 제목 수정", result.getTitle());
  }

  @Test
  @DisplayName("잘못된 도서 업데이트 테스트")
  void test4() {
    Book book = Book.create(BookFixture.bookCreate());

    BookUpdateInfo dto =
        new BookUpdateInfo(null, List.of("소설"), "도서 설명", "도서 저자", null, null, null);

    assertThrows(IllegalArgumentException.class, () -> book.update(dto));
  }
}
