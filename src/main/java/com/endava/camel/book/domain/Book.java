package com.endava.camel.book.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book {

    @Id
    @SequenceGenerator(name = "book_id_generator", sequenceName = "book_sequence", allocationSize = 1)
    @GeneratedValue(generator = "book_id_generator")
    private Long id;

    @Column(name = "publish_year")
    private int publishYear;

    private String title;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    private String isbn;

    public static Book from(Object csv) {
        String[] parts = ((String) csv).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        return Book.builder()
                .publishYear(Integer.parseInt(parts[0]))
                .title(stripQuotes(parts[1]))
                .authors(makeAuthors(Arrays.asList(parts[2].split("\\|"))))
                .isbn(parts[3])
                .build();
    }

    private static List<Author> makeAuthors(List<String> authors) {
        return authors.stream()
                .map(Author::new)
                .collect(Collectors.toList());
    }

    private static String stripQuotes(String part) {
        return part.startsWith("\"") ? part.substring(1, part.length() - 1) : part;
    }
}
