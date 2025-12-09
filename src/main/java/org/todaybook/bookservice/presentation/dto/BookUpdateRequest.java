package org.todaybook.bookservice.presentation.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookUpdateRequest {
  private String title;
  private List<String> categories;
  private String description;
  private String author;
  private String publisher;
  private LocalDate publishedAt;
  private String thumbnail;
}
