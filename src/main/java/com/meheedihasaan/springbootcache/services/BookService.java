package com.meheedihasaan.springbootcache.services;

import com.meheedihasaan.springbootcache.entities.Book;
import com.meheedihasaan.springbootcache.models.CreateBookRequest;
import com.meheedihasaan.springbootcache.models.UpdateBookRequest;
import com.meheedihasaan.springbootcache.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Cacheable(cacheNames = "books")
    public List<Book> getAll() {
        log.info("Getting all books from DB");
        return bookRepository.findAll();
    }

    @Cacheable(cacheNames = "book", key = "#id", unless = "#result==null")
    public Book getOne(UUID id) {
        log.info("Getting single book from DB");
        return bookRepository.findById(id).orElse(null);
    }

    @CacheEvict(cacheNames = "books", allEntries = true)
    public UUID create(CreateBookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book = bookRepository.save(book);
        return book.getId();
    }

    @CachePut(cacheNames = "book", key = "#id")
    @CacheEvict(cacheNames = "books", allEntries = true)
    public Book update(UUID id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            log.error("Book with id: {} not found", id);
            return null;
        }

        log.info("Updating single book");

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        return bookRepository.save(book);
    }

    @Caching(evict = {@CacheEvict(cacheNames = "books", allEntries = true), @CacheEvict(cacheNames = "book", key = "#id")})
    public void delete(UUID id) {
        bookRepository.deleteById(id);
    }
}
