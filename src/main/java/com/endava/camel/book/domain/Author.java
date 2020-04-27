package com.endava.camel.book.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(exclude = "id")
public class Author {
    @Id
    @SequenceGenerator(name = "author_id_generator", sequenceName = "author_sequence", allocationSize = 1)
    @GeneratedValue(generator = "author_id_generator")
    private Long id;

    @Column(name = "author_name")
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
