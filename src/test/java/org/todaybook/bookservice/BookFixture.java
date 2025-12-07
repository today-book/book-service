package org.todaybook.bookservice;

import java.time.LocalDateTime;
import java.util.List;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;
import org.todaybook.bookservice.domain.dto.BookUpdateInfo;

public class BookFixture {
  public static BookCreateInfo bookCreate() {
    return new BookCreateInfo(
        "0000000000001",
        "도서 제목",
        List.of("IT", "Programming"),
        "도서 소개",
        "홍길동",
        "출판사",
        LocalDateTime.of(2025, 1, 1, 0, 0),
        "http://example.com/thumb.png");
  }

  public static BookUpdateInfo bookUpdate() {
    return new BookUpdateInfo(
        "도서 제목",
        List.of("IT", "Programming", "AI"),
        "도서 소개글을 수정합니다.",
        "홍길동",
        "출판사",
        LocalDateTime.of(2025, 1, 1, 0, 0),
        "http://example.com/thumb.png");
  }
}
