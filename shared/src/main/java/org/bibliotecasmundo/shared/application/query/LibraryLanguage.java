package org.bibliotecasmundo.shared.application.query;

import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import org.bibliotecasmundo.shared.domain.book.Book;
import org.bibliotecasmundo.shared.infrastructure.config.AppConfig;
import org.bibliotecasmundo.shared.infrastructure.query.QueryFactory;

import java.util.Map;

@EqualsAndHashCode(of = "name", cacheStrategy = EqualsAndHashCode.CacheStrategy.LAZY)
public class LibraryLanguage implements Language {
    private final String name;
    private final Map<QueryParameter, String> parameterMap;

    public LibraryLanguage(String name, Map<QueryParameter, String> parameterMap) {
        this.name = name;
        this.parameterMap = parameterMap;
    }

    public static Language buildFromConfiguration(AppConfig appConfig) {
        return buildFromConfiguration(appConfig.getConfigParam(AppConfig.APP_NAME, "Lenguaje Biblioteca"), appConfig);
    }

    public static Language buildFromConfiguration(String name, AppConfig appConfig) {
        Map<QueryParameter, String> parameterMap = ImmutableMap.of(
                QueryParameter.AUTHOR, appConfig.getConfigParam(AppConfig.QUERY_TOKENS_AUTHOR, "autor"),
                QueryParameter.TITLE, appConfig.getConfigParam(AppConfig.QUERY_TOKENS_TITLE, "titulo"),
                QueryParameter.RESPONSE, appConfig.getConfigParam(AppConfig.RESPONSE_TOKEN, "book")
        );
        return new LibraryLanguage(name, parameterMap);
    }

    @Override
    public String getCommonName() {
        return name;
    }

    @Override
    public String getParameterName(QueryParameter parameter) {
        return parameterMap.get(parameter);
    }

    @Override
    public Query translateFromCommonLanguage(String query) throws UntranslatableQueryException {
        Language commonLanguage = Language.commonLanguage();
        final Query inCommonLanguage = QueryFactory.parseFromLanguage(commonLanguage, query);
        String searchTerm = inCommonLanguage.getSearchTerm();
        if (isAuthorQuery(inCommonLanguage, commonLanguage)) {
            return QueryFactory.parseFromLanguage(this, buildAuthorQuery(searchTerm));
        }

        if (isTitleQuery(inCommonLanguage, commonLanguage)) {
            return QueryFactory.parseFromLanguage(this, buildBookTitleQuery(searchTerm));
        }

        throw new UntranslatableQueryException(query, this);
    }

    @Override
    public String translateToCommonLanguage(Query query) throws UntranslatableQueryException {
        String searchTerm = query.getSearchTerm();
        Language commonLanguage = Language.commonLanguage();
        if (isAuthorQuery(query, this)) {
            return commonLanguage.getParameterName(QueryParameter.AUTHOR) + " " + searchTerm;
        }

        if (isTitleQuery(query, this)) {
            return commonLanguage.getParameterName(QueryParameter.TITLE) + " " + searchTerm;
        }

        throw new UntranslatableQueryException(query.getQueryString(), commonLanguage);
    }

    @Override
    public QueryResponse<Book> translateResponseFromCommonLanguage(String response) throws UntranslatableQueryResponseException {
        String replaced = response.replace(Language.commonLanguage().getParameterName(QueryParameter.RESPONSE), getParameterName(QueryParameter.RESPONSE));
        return new BookQueryResponse(replaced, this);
    }

    @Override
    public <T> String translateResponseToCommonLanguage(QueryResponse<T> response) throws UntranslatableQueryResponseException {
        return response.getResponseString().replace(getParameterName(QueryParameter.RESPONSE), Language.commonLanguage().getParameterName(QueryParameter.RESPONSE));
    }

    private boolean isAuthorQuery(Query query, Language language) {
        return query.getQueryString().contains(language.getParameterName(QueryParameter.AUTHOR));
    }

    private boolean isTitleQuery(Query query, Language language) {
        return query.getQueryString().contains(language.getParameterName(QueryParameter.TITLE));
    }

    private String buildAuthorQuery(String term) {
        return getParameterName(QueryParameter.AUTHOR) + " " + term;
    }

    private String buildBookTitleQuery(String term) {
        return getParameterName(QueryParameter.TITLE) + " " + term;
    }
}
