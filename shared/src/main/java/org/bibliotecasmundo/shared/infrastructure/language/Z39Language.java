package org.bibliotecasmundo.shared.infrastructure.language;

import com.google.common.collect.ImmutableMap;
import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.application.query.QueryLanguage;
import org.bibliotecasmundo.shared.application.query.QueryParameter;
import org.bibliotecasmundo.shared.infrastructure.query.QueryFactory;

import java.util.Map;

public class Z39Language implements QueryLanguage {
    private static final Z39Language INSTANCE = new Z39Language();
    private static final Map<QueryParameter, String> parameterTranslationMap = ImmutableMap.<QueryParameter, String>builder()
            .put(QueryParameter.TITLE, "Get Title")
            .put(QueryParameter.AUTHOR, "Get Author")
            .build();

    private Z39Language() {
    }

    public static QueryLanguage getInstance() {
        return INSTANCE;
    }

    @Override
    public String getCommonName() {
        return "Z39.50";
    }

    @Override
    public String getParameterName(QueryParameter parameter) {
        return parameterTranslationMap.get(parameter);
    }

    @Override
    public Query translateFromCommonLanguage(String query) {
        return QueryFactory.parseFromLanguage(getInstance(), query);
    }

    @Override
    public String translateToCommonLanguage(Query query) {
        // This IS the common language. Return query as is.
        return query.getQueryString();
    }
}
