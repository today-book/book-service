package org.todaybook.bookservice.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRegisterRequest {
  private String isbn;
  private String title;
  private List<String> categories;
  private String description;
  private String author;
  private String publisher;
  private LocalDateTime publishedAt;
  private String thumbnail;
}
