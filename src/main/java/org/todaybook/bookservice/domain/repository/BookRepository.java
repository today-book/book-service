package org.todaybook.bookservice.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.BookId;

public interface BookRepository extends JpaRepository<Book, BookId> {
  @Query("SELECT b FROM Book b WHERE b.id in :ids")
  List<Book> findByIds(List<BookId> ids);

  @Query("SELECT b FROM Book b WHERE b.isbn in :isbns")
  List<Book> findByIsbns(List<String> isbns);
}
