package com.rookies4.MySpringbootLab.repository;

import com.rookies4.MySpringbootLab.entity.Book;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@org.springframework.test.context.ActiveProfiles("test")
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testCreateBook() {
        Book book = new Book("스프링 부트 입문", "홍길동", "9788956746425",
                LocalDate.of(2023, 1, 15), 30000);

        Book saved = bookRepository.save(book);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("스프링 부트 입문");
    }

    @Test
    void testFindByIsbn() {
        Book book = new Book("JPA 프로그래밍", "박둘리", "9788956746432",
                LocalDate.of(2024, 3, 24), 35000);
        bookRepository.save(book);

        Optional<Book> found = bookRepository.findByIsbn("9788956746432");

        assertThat(found).isPresent();
        assertThat(found.get().getAuthor()).isEqualTo("박둘리");
    }

    @Test
    void testFindByAuthor() {
        bookRepository.saveAll(List.of(
                new Book("스프링 부트 입문", "홍길동", "9788956746426", LocalDate.now(), 30000),
                new Book("스프링 클라우드", "홍길동", "9788956746427", LocalDate.now(), 38000)
        ));

        List<Book> books = bookRepository.findByAuthor("홍길동");

        assertThat(books).hasSize(2);
    }

    @Test
    @Rollback(false)
    void testUpdateBook() {
        Book book = new Book("스프링 부트 입문", "홍길동", "9788956746428", LocalDate.now(), 30000);
        Book saved = bookRepository.save(book);

        saved.setPrice(32000);
        Book updated = bookRepository.save(saved);

        assertThat(updated.getPrice()).isEqualTo(32000);
    }

    @Test
    @Rollback(value = false)
    void testDeleteBook() {
        Book book = new Book("JPA 프로그래밍", "박둘리", "9788956746429", LocalDate.now(), 35000);
        Book saved = bookRepository.save(book);

        bookRepository.deleteById(saved.getId());

        assertThat(bookRepository.findById(saved.getId())).isEmpty();
    }
}

