package org.todaybook.bookservice.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public record BookCreate (
	String isbn,
	String title,
	List<String> categories,
	String description,
	String author,
	String publisher,
	LocalDateTime publishedAt,
	String thumbnail
) {}
