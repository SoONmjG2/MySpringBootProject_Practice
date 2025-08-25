package com.rookies4.MySpringbootLab.service;

import com.rookies4.MySpringbootLab.controller.dto.BookDTO;
import com.rookies4.MySpringbootLab.entity.Book;
import com.rookies4.MySpringbootLab.exception.BusinessException;
import com.rookies4.MySpringbootLab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public List<BookDTO.BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    public BookDTO.BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        return BookDTO.BookResponse.from(book);
    }


    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        return BookDTO.BookResponse.from(book);
    }

    public List<BookDTO.BookResponse> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author).stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        Book saved = bookRepository.save(request.toEntity());
        return BookDTO.BookResponse.from(saved);
    }

    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));

        if (request.getTitle() != null) {
            if (request.getTitle().isBlank()) {
                throw new BusinessException("제목은 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST);
            }
            existBook.setTitle(request.getTitle());
        }

        if (request.getAuthor() != null) {
            if (request.getAuthor().isBlank()) {
                throw new BusinessException("저자는 비어 있을 수 없습니다.", HttpStatus.BAD_REQUEST);
            }
            existBook.setAuthor(request.getAuthor());
        }


        if (request.getPrice() != null) {
            existBook.setPrice(request.getPrice());
        }


        if (request.getPublishDate() != null) {
            existBook.setPublishDate(request.getPublishDate());
        }

        return BookDTO.BookResponse.from(bookRepository.save(existBook));
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
    }
}
