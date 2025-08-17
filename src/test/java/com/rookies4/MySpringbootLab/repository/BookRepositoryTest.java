// com.rookies4.MySpringbootLab.repository.BookRepositoryTest
package com.rookies4.MySpringbootLab.repository;

import com.rookies4.MySpringbootLab.entity.Book;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // H2 대체 금지 → MariaDB 사용
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest {

    @Autowired
    BookRepository repo;

    //data
    private static final Book SRC1 = new Book(
            "스프링 부트 입문", "홍길동", "9788956746425",
            LocalDate.parse("2025-05-07"), 30000);

    private static final Book SRC2 = new Book(
            "JPA 프로그래밍", "박둘리", "9788956746432",
            LocalDate.parse("2025-04-30"), 35000);

    /**ISBN 기준*/
    private void upsert(Book src) {
        var opt = repo.findByIsbn(src.getIsbn());
        if (opt.isEmpty()) {
            repo.save(new Book(src.getTitle(), src.getAuthor(), src.getIsbn(), src.getPublishDate(), src.getPrice()));
        } else {
            var b = opt.get();
            b.setTitle(src.getTitle());
            b.setAuthor(src.getAuthor());
            b.setPublishDate(src.getPublishDate());
            b.setPrice(src.getPrice());
            repo.save(b);
        }
    }

    //도서 등록
    @Test @Order(1) @Commit
    void testCreateBook() {
        upsert(SRC1);
        upsert(SRC2);
        assertThat(repo.count()).isGreaterThanOrEqualTo(2);
    }

    //ISBN으로 조회
    @Test @Order(2)
    void testFindByIsbn() {
        var found = repo.findByIsbn(SRC1.getIsbn());
        assertThat(found).isPresent();
        assertThat(found.get().getAuthor()).isEqualTo("홍길동");
        assertThat(found.get().getTitle()).isEqualTo("스프링 부트 입문");
    }

    //저자로 도서 목록 조회
    @Test @Order(3)
    void testFindByAuthor() {
        List<Book> list = repo.findByAuthor("박둘리");
        assertThat(list).isNotEmpty();
        assertThat(list.get(0).getIsbn()).isEqualTo(SRC2.getIsbn());
    }

    //도서 정보 수정
    @Test @Order(4) @Commit
    void testUpdateBook() {
        var book = repo.findByIsbn(SRC1.getIsbn()).orElseThrow();
        book.setTitle("스프링 부트 입문(개정판)");
        book.setPrice(31000);
        var updated = repo.save(book);
        assertThat(updated.getTitle()).contains("개정판");
        assertThat(updated.getPrice()).isEqualTo(31000);
    }

    //도서 삭제
    @Test @Order(5) @Commit
    void testDeleteBook() {
        var book = repo.findByIsbn(SRC1.getIsbn()).orElse(null);
        if (book != null) repo.delete(book);
        assertThat(repo.findByIsbn(SRC1.getIsbn())).isEmpty();

        //복구
        upsert(SRC1);
        assertThat(repo.findByIsbn(SRC1.getIsbn())).isPresent();
    }
}
