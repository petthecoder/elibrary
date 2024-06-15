package com.boskman.elibrary.listbook;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ListBookId implements java.io.Serializable {
    private Long listId;
    private Long bookId;
}
