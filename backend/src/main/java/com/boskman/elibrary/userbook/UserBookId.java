package com.boskman.elibrary.userbook;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class UserBookId implements java.io.Serializable {
    private Long userId;
    private Long bookId;
}
