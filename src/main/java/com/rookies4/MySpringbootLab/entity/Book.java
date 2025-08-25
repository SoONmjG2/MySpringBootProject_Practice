package com.rookies4.MySpringbootLab.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "book",
        indexes = {
                @Index(name = "idx_book_isbn", columnList = "isbn", unique = true),
                @Index(name = "idx_book_author", columnList = "author")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, length = 20, unique = true)
    private String isbn;

    @Column(nullable = false)
    private LocalDate publishDate;

    @Column(nullable = false)
    private Integer price;

    public Book(String title, String author, String isbn, LocalDate publishDate, Integer price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.price = price;
    }
}
