package com.endava.camel.book.service;

import com.endava.camel.book.domain.Author;
import com.endava.camel.book.domain.Book;
import com.endava.camel.book.repository.AuthorRepository;
import com.endava.camel.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Handler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    @Handler
    public Book saveBook(Book book) {
        book.setAuthors(makeUniqueAuthors(book));
        return bookRepository.save(book);
    }

    private List<Author> makeUniqueAuthors(Book book) {
        Set<Author> authors = authorRepository.findByNameIn(makeAuthorsNames(book.getAuthors()));
        Map<String, Author> authorsByName = groupAuthorsByName(authors);
        return book.getAuthors().stream()
                .map(author -> authorsByName.getOrDefault(author.getName(), author))
                .collect(Collectors.toList());
    }

    private Map<String, Author> groupAuthorsByName(Set<Author> authors) {
        return authors.stream()
                .collect(groupingBy(Author::getName,
                        collectingAndThen(Collectors.reducing((a1, a2) -> a1), author -> author.orElse(null))));
    }

    private List<String> makeAuthorsNames(List<Author> authors) {
        return authors.stream()
                .map(Author::getName)
                .collect(Collectors.toList());
    }
}
