package com.endava.camel.book.repository;

import com.endava.camel.book.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Set<Author> findByNameIn(List<String> names);
}
