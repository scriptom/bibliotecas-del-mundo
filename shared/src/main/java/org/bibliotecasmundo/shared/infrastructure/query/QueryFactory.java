package org.bibliotecasmundo.shared.infrastructure.query;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.application.query.Language;
import org.bibliotecasmundo.shared.application.query.QueryParameter;
import org.bibliotecasmundo.shared.application.query.UntranslatableQueryException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QueryFactory {
    public static Query createAuthorQuery(Language language, String author) {
        return new AuthorQuery(author, language);
    }
    public static Query createTitleQuery(Language language, String title) {
        return new BookTitleQuery(title, language);
    }
    public static Query parseFromLanguage(Language language, String query) {
        Pattern authorRegex = buildRegexForAuthorQuery(language);
        Pattern titleRegex = buildRegexForTitleQuery(language);

        // Try to extract author
        Matcher authorMatcher = authorRegex.matcher(query);
        boolean isAuthorRegexp = authorMatcher.matches();
        if (isAuthorRegexp) {
            String searchTerm = authorMatcher.group(1);
            return new AuthorQuery(searchTerm, language);
        }

        // We failed. Try to extract book title
        Matcher titleMatcher = titleRegex.matcher(query);
        boolean isTitleRegexp = titleMatcher.matches();
        if (isTitleRegexp) {
            String searchTerm = titleMatcher.group(1);
            return new BookTitleQuery(searchTerm, language);
        }

        // We failed. Throw an exception
        throw new UntranslatableQueryException(query);
    }

    private static Pattern buildRegexForAuthorQuery(Language language) {
        return Pattern.compile("^" + language.getParameterName(QueryParameter.AUTHOR) + " (.*)$");
    }

    private static Pattern buildRegexForTitleQuery(Language language) {
        return Pattern.compile("^" + language.getParameterName(QueryParameter.TITLE) + " (.*)$");
    }
}
