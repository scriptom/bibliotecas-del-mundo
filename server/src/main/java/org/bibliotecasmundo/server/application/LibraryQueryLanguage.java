package org.bibliotecasmundo.server.application;

import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import org.bibliotecasmundo.server.infrastructure.config.ServerConfigConstants;
import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.application.query.QueryLanguage;
import org.bibliotecasmundo.shared.application.query.QueryParameter;
import org.bibliotecasmundo.shared.application.query.UntranslatableQueryException;
import org.bibliotecasmundo.shared.infrastructure.config.AppConfig;
import org.bibliotecasmundo.shared.infrastructure.query.QueryFactory;

import java.util.Map;

@EqualsAndHashCode(of = "name", cacheStrategy = EqualsAndHashCode.CacheStrategy.LAZY)
public class LibraryQueryLanguage implements QueryLanguage {
    private final String name;
    private final Map<QueryParameter, String> parameterMap;

    public LibraryQueryLanguage(String name, Map<QueryParameter, String> parameterMap) {
        this.name = name;
        this.parameterMap = parameterMap;
    }

    public static QueryLanguage buildFromConfiguration(AppConfig appConfig) {
        return buildFromConfiguration(appConfig.getConfigParam(ServerConfigConstants.APP_NAME, "Lenguaje Biblioteca"), appConfig);
    }

    public static QueryLanguage buildFromConfiguration(String name, AppConfig appConfig) {
        Map<QueryParameter, String> parameterMap = ImmutableMap.of(
                QueryParameter.AUTHOR, appConfig.getConfigParam(ServerConfigConstants.QUERY_TOKENS_AUTHOR, "autor"),
                QueryParameter.TITLE, appConfig.getConfigParam(ServerConfigConstants.QUERY_TOKENS_TITLE, "titulo")
        );
        return new LibraryQueryLanguage(name, parameterMap);
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
        QueryLanguage commonLanguage = QueryLanguage.commonLanguage();
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
        QueryLanguage commonLanguage = QueryLanguage.commonLanguage();
        if (isAuthorQuery(query, this)) {
            return commonLanguage.getParameterName(QueryParameter.AUTHOR) + " " + searchTerm;
        }

        if (isTitleQuery(query, this)) {
            return commonLanguage.getParameterName(QueryParameter.TITLE) + " " + searchTerm;
        }

        throw new UntranslatableQueryException(query.getQueryString(), commonLanguage);
    }

    private boolean isAuthorQuery(Query query, QueryLanguage language) {
        return query.getQueryString().contains(language.getParameterName(QueryParameter.AUTHOR));
    }

    private boolean isTitleQuery(Query query, QueryLanguage language) {
        return query.getQueryString().contains(language.getParameterName(QueryParameter.TITLE));
    }

    private String buildAuthorQuery(String term) {
        return getParameterName(QueryParameter.AUTHOR) + " " + term;
    }

    private String buildBookTitleQuery(String term) {
        return getParameterName(QueryParameter.TITLE) + " " + term;
    }
}
