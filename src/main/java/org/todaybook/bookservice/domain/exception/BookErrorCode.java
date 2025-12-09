package org.todaybook.bookservice.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.todaybook.commoncore.error.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum BookErrorCode implements ErrorCode {
  NOT_FOUND("BOOK.NOT_FOUND", HttpStatus.NOT_FOUND.value()),
  ALREADY_EXISTS("BOOK.ALREADY_EXISTS", HttpStatus.CONFLICT.value()),
  ;

  private final String code;
  private final int status;
}
