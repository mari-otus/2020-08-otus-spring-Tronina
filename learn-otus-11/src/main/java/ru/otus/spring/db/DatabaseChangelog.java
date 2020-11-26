package ru.otus.spring.db;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.Arrays;
import java.util.List;

/**
 * Набор изменений для mongock.
 *
 * @author Mariya Tronina
 */
@ChangeLog
public class DatabaseChangelog {

    private static final List<Book> BOOK_LIST = Arrays.asList(
            Book.builder().id("1").name("Война и мир")
                    .yearEdition(1981)
                    .authors(Arrays.asList(Author.builder().id("1").fio("Толстой Лев Николаевич").build()))
                    .genres(Arrays.asList(Genre.builder().id("1").name("Роман").build()))
                    .build(),
            Book.builder().id("2").name("Хаджи-Мурат")
                    .yearEdition(1975)
                    .authors(Arrays.asList(Author.builder().id("1").fio("Толстой Лев Николаевич").build()))
                    .genres(Arrays.asList(Genre.builder().id("3").name("Повесть").build()))
                    .build(),
            Book.builder().id("3").name("Евгений Онегин")
                    .yearEdition(1987)
                    .authors(Arrays.asList(Author.builder().id("2").fio("Пушкин Александр Сергеевич").build()))
                    .genres(Arrays.asList(Genre.builder().id("1").name("Роман").build(), Genre.builder().id("4").name("Стихотворение").build()))
                    .build(),
            Book.builder().id("4").name("Сказка о рыбаке и рыбке")
                    .yearEdition(2015)
                    .authors(Arrays.asList(Author.builder().id("2").fio("Пушкин Александр Сергеевич").build()))
                    .genres(Arrays.asList(Genre.builder().id("4").name("Стихотворение").build(), Genre.builder().id("5").name("Сказка").build()))
                    .build(),
            Book.builder().id("5").name("Песнь о вещем Олеге")
                    .yearEdition(2015)
                    .authors(Arrays.asList(Author.builder().id("2").fio("Пушкин Александр Сергеевич").build()))
                    .genres(Arrays.asList(Genre.builder().id("3").name("Повесть").build(), Genre.builder().id("4").name("Стихотворение").build()))
                    .build(),
            Book.builder().id("6").name("Полное собрание повестей и рассказов о Шерлоке Холмсе в одном томе")
                    .yearEdition(1998)
                    .authors(Arrays.asList(Author.builder().id("3").fio("Дойл Артур Конан").build()))
                    .genres(Arrays.asList(Genre.builder().id("2").name("Рассказ").build(), Genre.builder().id("3").name("Повесть").build()))
                    .build(),
            Book.builder().id("7").name("Улитка на склоне")
                    .yearEdition(2001)
                    .authors(Arrays.asList(Author.builder().id("4").fio("Стругацкий Аркадий Натанович").build(), Author.builder().id("5").fio("Стругацкий Борис Натанович").build()))
                    .genres(Arrays.asList(Genre.builder().id("1").name("Роман").build(), Genre.builder().id("8").name("Фантастика").build()))
                    .build(),
            Book.builder().id("8").name("Понедельник начинается в субботу")
                    .yearEdition(2001)
                    .authors(Arrays.asList(Author.builder().id("4").fio("Стругацкий Аркадий Натанович").build(), Author.builder().id("5").fio("Стругацкий Борис Натанович").build()))
                    .genres(Arrays.asList(Genre.builder().id("3").name("Повесть").build(), Genre.builder().id("8").name("Фантастика").build(), Genre.builder().id("9").name("Юмор").build()))
                    .build()
    );

    @ChangeSet(order = "001", id="clear", author = "m.tronina", runAlways = true)
    public void clear(MongoTemplate mongoTemplate){
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(Book.class)).drop();
    }

    @ChangeSet(order = "002", id="initBook", author = "m.tronina", runAlways = true)
    public void initBook(MongoTemplate mongoTemplate){
        mongoTemplate.insertAll(BOOK_LIST);
    }

}

