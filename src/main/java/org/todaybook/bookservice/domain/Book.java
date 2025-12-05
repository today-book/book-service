package org.todaybook.bookservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
import org.todaybook.bookservice.domain.dto.BookCreate;
import org.todaybook.bookservice.domain.dto.BookUpdate;

@Table(schema = "book", name = "p_books")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

  @EmbeddedId private BookId id;

  @Column(nullable = false, length = 20)
  private String isbn;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "text[]")
  @JdbcTypeCode(SqlTypes.ARRAY)
  private List<String> categories;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private String author;

  @Column private String publisher;

  @Column private LocalDateTime publishedAt;

  @Column(columnDefinition = "TEXT")
  private String thumbnail;

  @Column(nullable = false)
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(nullable = false)
  @LastModifiedDate
  private LocalDateTime updatedAt;

  public static Book create(BookCreate dto) {
    validateIsbn(dto.isbn());
    validateTitle(dto.title());
    validateDescription(dto.description());
    validateAuthor(dto.author());

    Book book = new Book();

    book.id = BookId.generateId();
    book.isbn = dto.isbn();
    book.title = dto.title();
    book.categories = dto.categories();
    book.description = dto.description();
    book.author = dto.author();
    book.publisher = dto.publisher();
    book.publishedAt = dto.publishedAt();
    book.thumbnail = dto.thumbnail();

    return book;
  }

  public Book update(BookUpdate dto) {
    validateTitle(dto.title());
    validateDescription(dto.description());
    validateAuthor(dto.author());

    this.title = dto.title();
    this.categories = dto.categories();
    this.description = dto.description();
    this.author = dto.author();
    this.publisher = dto.publisher();
    this.publishedAt = dto.publishedAt();
    this.thumbnail = dto.thumbnail();

    return this;
  }

  public static void validateIsbn(String isbn) {
    if (isbn == null || isbn.isBlank()) {
      throw new IllegalArgumentException();
    }
  }

  public static void validateTitle(String title) {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException();
    }
  }

  public static void validateDescription(String description) {
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException();
    }
  }

  public static void validateAuthor(String author) {
    if (author == null || author.isBlank()) {
      throw new IllegalArgumentException();
    }
  }
}
