package org.todaybook.bookservice.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.BookId;

public interface BookRepository extends Repository<Book, BookId> {
  Optional<Book> findById(BookId id);

  @Query("SELECT b FROM Book b WHERE b.id in :ids")
  List<Book> findByIds(List<BookId> ids);

  Book save(Book book);
}
