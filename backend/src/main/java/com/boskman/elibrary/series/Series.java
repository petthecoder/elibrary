package com.boskman.elibrary.series;

import com.boskman.elibrary.book.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seriesId;

    @Column(nullable = false)
    private String title;

    private String description;

    @OneToMany(mappedBy = "series")
    private Set<Book> books = new HashSet<>();
}

