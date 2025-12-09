package org.todaybook.bookservice.domain.exception;

import org.todaybook.commoncore.error.AbstractServiceException;

public class BookNotFoundException extends AbstractServiceException {

  public BookNotFoundException(Object... errorArgs) {
    super(BookErrorCode.NOT_FOUND, errorArgs);
  }
}
