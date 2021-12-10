package org.bibliotecasmundo.shared.application.query;

import org.bibliotecasmundo.shared.domain.book.Book;
import org.bibliotecasmundo.shared.domain.book.BookAuthor;
import org.bibliotecasmundo.shared.domain.book.BookTitle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookQueryResponse implements QueryResponse<Book> {
    private final String responseString;
    private final Language language;

    public BookQueryResponse(String responseString, Language language) {
        this.responseString = responseString;
        this.language = language;
    }

    @Override
    public String getResponseString() {
        return responseString;
    }

    @Override
    public Language getLanguage() {
        return language;
    }

    @Override
    public String[] getResultTerms() {
        return responseString.split("\n");
    }

    @Override
    public List<Book> getResults() {
        return Arrays.stream(getResultTerms()).map(term -> {
            String cleaned = term.replace(language.getParameterName(QueryParameter.RESPONSE) + " ", "");
            String[] parts =  cleaned.split("\\|");
            return Book.create(BookTitle.create(parts[0]), BookAuthor.create(parts[1]));
        }).collect(Collectors.toList());
    }
}
