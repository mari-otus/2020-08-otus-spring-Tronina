package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.SetUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

/**
 * Книга.
 *
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
@NamedEntityGraph(name = "author-genre-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("authors"),
                @NamedAttributeNode("genres")
        })
public class Book {

    /**
     * Идентификатор книги.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Наименование книги.
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * Год издания книги.
     */
    @Column(name = "year_edition")
    private int yearEdition;
    /**
     * Список авторов книги.
     */
    @ManyToMany(targetEntity = Author.class, fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"),
               inverseJoinColumns = @JoinColumn(name = "author_id"))
    @OrderBy(value = "id")
    private Set<Author> authors;
    /**
     * Список жанров, к которым относится книга.
     */
    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"),
               inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @OrderBy(value = "id")
    private Set<Genre> genres;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;

        return yearEdition == book.yearEdition &&
                id.equals(book.id) &&
                name.equals(book.name) &&
                SetUtils.isEqualSet(authors, book.authors) &&
                SetUtils.isEqualSet(genres, book.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, yearEdition, authors, genres);
    }
}
