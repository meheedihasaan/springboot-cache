package com.meheedihasaan.springbootcache.services;

import com.meheedihasaan.springbootcache.entities.Book;
import com.meheedihasaan.springbootcache.models.CreateBookRequest;
import com.meheedihasaan.springbootcache.models.UpdateBookRequest;
import com.meheedihasaan.springbootcache.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAll() {
        log.info("Getting all books from DB");
        return bookRepository.findAll();
    }

    public Book getOne(UUID id) {
        log.info("Getting single book from DB");
        return bookRepository.findById(id).orElse(null);
    }

    public UUID create(CreateBookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book = bookRepository.save(book);
        return book.getId();
    }

    public void update(UUID id, UpdateBookRequest request) {
        Book book = getOne(id);
        if (book == null) {
            return;
        }

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        bookRepository.save(book);
    }

    public void delete(UUID id) {
        bookRepository.deleteById(id);
    }
}
