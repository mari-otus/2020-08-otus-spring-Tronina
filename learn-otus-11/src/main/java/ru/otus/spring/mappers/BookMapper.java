package ru.otus.spring.mappers;

import org.mapstruct.Mapper;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;

/**
 * @author MTronina
 */
@Mapper
public interface BookMapper {

    BookDto bookToBookDto(Book book);
    Book bookDtoToBook(BookDto bookDto);
}
