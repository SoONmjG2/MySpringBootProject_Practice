//package com.rookies4.MySpringbootLab.controller;
//
//import com.rookies4.MySpringbootLab.entity.Book;
//import com.rookies4.MySpringbootLab.exception.BusinessException;
//import com.rookies4.MySpringbootLab.repository.BookRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/books")
//public class BookRestController {
//    private final BookRepository bookRepository;
//
//    // 등록
//    @PostMapping
//    public Book create(@RequestBody Book book){
//        return bookRepository.save(book);
//    }
//
//    // 전체 목록 조회
//    @GetMapping
//    public List<Book> getBooks() {
//        return bookRepository.findAll();
//    }
//
//    // ID로 조회
//    @GetMapping("/{id}")
//    public Book getBook(@PathVariable Long id){
//        return getIDExistBook(id);
//    }
//
//    // ISBN으로 조회
//    @GetMapping("/isbn/{isbn}")
//    public Book getByIsbn(@PathVariable String isbn) {
//        return getIsbnExistBook(isbn);
//    }
//
//    // 수정하기
//    @PutMapping("/{id}")
//    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetail){
//        Book existBook = bookRepository.findById(id)
//                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
//        existBook.setTitle(bookDetail.getTitle());
//        return bookRepository.save(existBook);
//    }
//
//    // 삭제하기
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteBook(@PathVariable Long id){
//        Book existBook = getIDExistBook(id);
//        bookRepository.delete(existBook);
//        return ResponseEntity.ok("Book이 삭제 되었습니다.");
//    }
//
//    private Book getIDExistBook(Long id) {
//        return bookRepository.findById(id)
//                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
//    }
//
//    private Book getIsbnExistBook(String isbn) {
//        return bookRepository.findByIsbn(isbn)
//                .orElseThrow(() -> new BusinessException("ISBN Not Found", HttpStatus.NOT_FOUND));
//    }
//}
