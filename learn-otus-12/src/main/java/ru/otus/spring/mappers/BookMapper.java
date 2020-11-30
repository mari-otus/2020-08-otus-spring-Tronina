package ru.otus.spring.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookSaveDto;

/**
 * @author Mariya Tronina
 */
@Mapper
public interface BookMapper {

    BookDto bookToBookDto(Book book);

    @Mapping(source = "book.id", target = "id")
    @Mapping(source = "book.authors", target = "authors")
    @Mapping(source = "book.genres", target = "genres")
    @Mapping(source = "bookSaveDto.name", target = "name")
    @Mapping(source = "bookSaveDto.yearEdition", target = "yearEdition")
    Book bookSaveDtoToBook(Book book, BookSaveDto bookSaveDto);
}
