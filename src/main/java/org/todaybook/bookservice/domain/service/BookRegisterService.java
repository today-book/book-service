package org.todaybook.bookservice.domain.service;

import java.util.List;
import org.todaybook.bookservice.domain.dto.BookCreateInfo;

public interface BookRegisterService {
  void register(List<BookCreateInfo> request);
}
