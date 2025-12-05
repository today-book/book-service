package org.todaybook.bookservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookId {

  @Column private UUID id;

  protected BookId(UUID id) {
    this.id = id;
  }

  public static BookId generateId() {
    return BookId.of(UUID.randomUUID());
  }

  public static BookId of(UUID id) {
    return new BookId(id);
  }

  public UUID toUUID() {
    return id;
  }

  public String toString() {
    return id.toString();
  }
}
