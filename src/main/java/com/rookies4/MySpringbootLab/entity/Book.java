package com.rookies4.MySpringbootLab.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "book",
        indexes = {
                @Index(name = "idx_book_isbn", columnList = "isbn", unique = true),
                @Index(name = "idx_book_author", columnList = "author")
        }
)
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=200)
    private String title;

    @Column(nullable=false, length=100)
    private String author;

    @Column(nullable=false, length=20, unique=true)
    private String isbn;

    @Column(nullable=false)
    private LocalDate publishDate;

    @Column(nullable=false)
    private Integer price;

    protected Book() {}
    public Book(String title, String author, String isbn, LocalDate publishDate, Integer price) {
        this.title = title; this.author = author; this.isbn = isbn;
        this.publishDate = publishDate; this.price = price;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }           public void setTitle(String t){ this.title=t; }
    public String getAuthor() { return author; }         public void setAuthor(String a){ this.author=a; }
    public String getIsbn() { return isbn; }             public void setIsbn(String i){ this.isbn=i; }
    public LocalDate getPublishDate(){ return publishDate; } public void setPublishDate(LocalDate d){ this.publishDate=d; }
    public Integer getPrice(){ return price; }           public void setPrice(Integer p){ this.price=p; }
}
