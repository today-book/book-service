package org.todaybook.bookservice.presentation.dto;

import java.util.List;
import java.util.UUID;

public record BookListResponse(List<BookResponse> found, List<UUID> failure) {}
