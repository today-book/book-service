package org.todaybook.bookservice.domain.service;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookBatchService {

  private final BookRepository repository;
  private final EntityManager entityManager;

  @Transactional
  public void batch(List<Book> chunk) {
    repository.saveAll(chunk);
    entityManager.flush();
    entityManager.clear();
  }
}
