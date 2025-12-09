package org.todaybook.bookservice.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.BookId;

public interface BookRepository extends JpaRepository<Book, BookId> {
  @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
  Optional<Book> findByIsbn(@Param("isbn") String isbn);

  @Query("SELECT b FROM Book b WHERE b.id in :ids")
  List<Book> findByIds(@Param("ids") List<BookId> ids);

  @Query("SELECT b FROM Book b WHERE b.isbn in :isbns")
  List<Book> findByIsbns(@Param("isbns") List<String> isbns);
}
