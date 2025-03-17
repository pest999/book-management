package io.github.alexshamrai.bookservice.repository;

import io.github.alexshamrai.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleAndAuthor(String title, String author);
}