package org.todaybook.bookservice;

import java.time.LocalDateTime;
import java.util.List;
import org.todaybook.bookservice.domain.dto.BookCreate;
import org.todaybook.bookservice.domain.dto.BookUpdate;

public class BookFixture {
  public static BookCreate bookCreate() {
    return new BookCreate(
        "0000000000001",
        "도서 제목",
        List.of("IT", "Programming"),
        "도서 소개글입니다.",
        "홍길동",
        "출판사",
        LocalDateTime.of(2025, 1, 1, 0, 0),
        "http://example.com/thumb.png");
  }

  public static BookUpdate bookUpdate() {
    return new BookUpdate(
        "도서 제목",
        List.of("IT", "Programming", "AI"),
        "도서 소개글을 수정합니다.",
        "홍길동",
        "출판사",
        LocalDateTime.of(2025, 1, 1, 0, 0),
        "http://example.com/thumb.png");
  }
}
