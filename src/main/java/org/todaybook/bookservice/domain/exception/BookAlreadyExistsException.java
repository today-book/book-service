package org.todaybook.bookservice.domain.exception;

import org.todaybook.commoncore.error.AbstractServiceException;

public class BookAlreadyExistsException extends AbstractServiceException {

  public BookAlreadyExistsException(Object... errorArgs) {
    super(BookErrorCode.ALREADY_EXISTS, errorArgs);
  }
}
