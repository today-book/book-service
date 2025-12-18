package org.todaybook.bookservice.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;
import org.todaybook.bookservice.domain.dto.BookUpdateInfo;
import org.todaybook.bookservice.domain.repository.BookRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookRegisterServiceImpl implements BookRegisterService {

  private final BookUpdateStrategy strategy;
  private final BookRepository repository;

  @Override
  public List<Book> register(List<BookCreateInfo> request) {
    // 요청 리스트 내 중복 ISBN 병합
    List<BookCreateInfo> merged = mergeCreateInfo(request);
    log.debug("[TODAY-BOOK] 도서 등록 요청 {}건 -> 병합 후 {}건", request.size(), merged.size());

    // DB에서 기존 도서 조회
    List<String> isbns = merged.stream().map(BookCreateInfo::isbn).toList();
    Map<String, Book> existing =
        repository.findByIsbns(isbns).stream()
            .collect(Collectors.toMap(Book::getIsbn, Function.identity()));

    // 신규/업데이트 리스트 생성
    List<Book> bookList = new ArrayList<>();

    for (BookCreateInfo createInfo : merged) {
      try {
        if (existing.containsKey(createInfo.isbn())) {
          Book book = existing.get(createInfo.isbn());

          BookUpdateInfo updateInfo = strategy.buildUpdateInfo(book, createInfo);
          bookList.add(book.update(updateInfo));

          log.debug("[TODAY-BOOK] 기존 도서 도메인 업데이트 (isbn={})", book.getIsbn());
        } else {
          bookList.add(Book.create(createInfo));

          log.debug("[TODAY-BOOK] 신규 도서 도메인 생성 (isbn={})", createInfo.isbn());
        }
      } catch (Exception e) {
        log.warn("[TODAY-BOOK] 도서 등록 요청 검증 실패 - skip (isbn={}, message={})", createInfo.isbn(), e.getMessage());
      }
    }

    return repository.saveAll(bookList);
  }

  private List<BookCreateInfo> mergeCreateInfo(List<BookCreateInfo> infos) {
    return infos.stream().collect(Collectors.groupingBy(BookCreateInfo::isbn)).values().stream()
        .map(
            books -> {
              if (books.size() == 1) {
                return books.getFirst();
              } else {
                log.debug("[TODAY-BOOK] 동일 ISBN({}) {}건 병합", books.getFirst().isbn(), books.size());
                return reduceCreateInfo(books);
              }
            })
        .toList();
  }

  private BookCreateInfo reduceCreateInfo(List<BookCreateInfo> infos) {
    return infos.stream()
        .reduce(
            (base, request) -> {
              List<String> categories =
                  strategy.updateCategories(base.categories(), request.categories());
              String description =
                  strategy.updateDescription(base.description(), request.description());
              String publisher = strategy.updatePublisher(base.publisher(), request.publisher());
              LocalDate publishedAt =
                  strategy.updatePublishedAt(base.publishedAt(), request.publishedAt());
              String thumbnail = strategy.updateThumbnail(base.thumbnail(), request.thumbnail());

              log.debug(
                  """
                [TODAY-BOOK] ISBN={} 병합 결과:
                  - categories: {} -> {}
                  - description: {} -> {}
                  - publisher: {} -> {}
                  - publishedAt: {} -> {}
                  - thumbnail: {} -> {}
                """,
                  request.isbn(),
                  base.categories(),
                  categories,
                  base.description(),
                  description,
                  base.publisher(),
                  publisher,
                  base.publishedAt(),
                  publishedAt,
                  base.thumbnail(),
                  thumbnail);

              return BookCreateInfo.builder()
                  .isbn(request.isbn())
                  .title(request.title())
                  .categories(categories)
                  .description(description)
                  .author(request.author())
                  .publisher(publisher)
                  .publishedAt(publishedAt)
                  .thumbnail(thumbnail)
                  .build();
            })
        .orElseThrow(() -> new IllegalStateException("[TODAY-BOOK] 병합할 데이터가 없습니다."));
  }
}
