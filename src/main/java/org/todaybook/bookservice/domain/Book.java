package org.todaybook.bookservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;
import org.todaybook.bookservice.domain.dto.BookUpdateInfo;

@Entity
@Getter
@ToString
@Table(schema = "book", name = "p_books")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

  @EmbeddedId private BookId id;

  @Column(nullable = false, length = 20)
  private String isbn;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String title;

  @Column(columnDefinition = "text[]")
  @JdbcTypeCode(SqlTypes.ARRAY)
  private List<String> categories;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private String author;

  @Column private String publisher;

  @Column private LocalDate publishedAt;

  @Column(columnDefinition = "TEXT")
  private String thumbnail;

  @Column(nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(nullable = false)
  @LastModifiedDate
  private LocalDateTime updatedAt;

  public static Book create(BookCreateInfo request) {
    validateIsbn(request.isbn());
    validateTitle(request.title());
    validateDescription(request.description());
    validateAuthor(request.author());

    Book book = new Book();

    book.id = BookId.generateId();
    book.isbn = request.isbn();
    book.title = request.title();
    book.categories = request.categories();
    book.description = request.description();
    book.author = request.author();
    book.publisher = request.publisher();
    book.publishedAt = request.publishedAt();
    book.thumbnail = request.thumbnail();

    return book;
  }

  public Book update(BookUpdateInfo request) {
    validateTitle(request.title());
    validateDescription(request.description());
    validateAuthor(request.author());

    this.title = request.title();
    this.categories = request.categories();
    this.description = request.description();
    this.author = request.author();
    this.publisher = request.publisher();
    this.publishedAt = request.publishedAt();
    this.thumbnail = request.thumbnail();

    return this;
  }

  public static void validateIsbn(String isbn) {
    if (isbn == null || isbn.isBlank()) {
      throw new IllegalArgumentException("도서 isbn은 비어있을 수 없습니다.");
    }
  }

  public static void validateTitle(String title) {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("도서 제목(title)은 비어있을 수 없습니다.");
    }
  }

  public static void validateDescription(String description) {
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("도서 소개(description)은 비어있을 수 없습니다.");
    }
  }

  public static void validateAuthor(String author) {
    if (author == null || author.isBlank()) {
      throw new IllegalArgumentException("도서 저자(author)는 비어있을 수 없습니다.");
    }
  }
}
