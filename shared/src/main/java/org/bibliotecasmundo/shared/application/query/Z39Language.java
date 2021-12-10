package org.bibliotecasmundo.shared.application.query;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.bibliotecasmundo.shared.infrastructure.query.QueryFactory;

import java.util.List;
import java.util.Map;

public class Z39Language implements Language {
    private static final Z39Language INSTANCE = new Z39Language();
    private static final Map<QueryParameter, String> parameterTranslationMap = ImmutableMap.<QueryParameter, String>builder()
            .put(QueryParameter.TITLE, "Get Title")
            .put(QueryParameter.AUTHOR, "Get Author")
            .put(QueryParameter.RESPONSE, "Title")
            .build();

    private Z39Language() {
    }

    public static Language getInstance() {
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

    @Override
    public QueryResponse<String> translateResponseFromCommonLanguage(String response) throws UntranslatableQueryResponseException {
        return new QueryResponse<String>() {
            @Override
            public String getResponseString() {
                return response;
            }

            @Override
            public Language getLanguage() {
                return Z39Language.getInstance();
            }

            @Override
            public String[] getResultTerms() {
                return response.split("\n");
            }

            @Override
            public List<String> getResults() {
                return Lists.newArrayList(response.split("\n"));
            }
        };
    }

    @Override
    public <T> String translateResponseToCommonLanguage(QueryResponse<T> response) throws UntranslatableQueryResponseException {
        return response.getResponseString();
    }
}
