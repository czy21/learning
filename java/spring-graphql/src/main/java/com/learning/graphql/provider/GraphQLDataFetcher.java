package com.learning.graphql.provider;

import com.learning.domain.entity.model.PageModel;
import com.learning.domain.entity.model.PageResult;
import graphql.com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetcher {

    private static List<Map<String, Object>> books = Arrays.asList(
            ImmutableMap.of("id", "book-1",
                    "name", "Harry Potter and the Philosopher's Stone",
                    "pageCount", "223",
                    "authorId", "author-1"),
            ImmutableMap.of("id", "book-2",
                    "name", "Moby Dick",
                    "pageCount", "635",
                    "authorId", "author-2"),
            ImmutableMap.of("id", "book-3",
                    "name", "Interview with the vampire",
                    "pageCount", "371",
                    "authorId", "author-3"),
            ImmutableMap.of("id", "book-4",
                    "name", "4",
                    "pageCount", "371",
                    "authorId", "author-3")
    );

    private static List<Map<String, Object>> authors = Arrays.asList(
            ImmutableMap.of("id", "author-1",
                    "firstName", "Joanne",
                    "lastName", "Rowling"),
            ImmutableMap.of("id", "author-2",
                    "firstName", "Herman",
                    "lastName", "Melville"),
            ImmutableMap.of("id", "author-3",
                    "firstName", "Anne",
                    "lastName", "Rice")
    );

    public DataFetcher<PageResult<Map<String, Object>>> findAllBook() {
        return env -> {
            PageResult<Map<String, Object>> pageResult = new PageResult<>();
            pageResult.setPage(new PageModel());
            pageResult.setList(books);
            return pageResult;
        };
    }

    public DataFetcher<Map<String, Object>> findBookById() {
        return env -> {
            String bookId = env.getArgument("id");
            return books
                    .stream()
                    .filter(book -> book.get("id").equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<Map<String, Object>> findAuthorById() {
        return env -> {
            Map<String, String> book = env.getSource();
            String authorId = book.get("authorId");
            return authors
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }
}