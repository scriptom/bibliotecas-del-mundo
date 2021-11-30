package org.bibliotecasmundo.shared.infrastructure.query;

import org.bibliotecasmundo.shared.application.query.QueryLanguage;
import org.bibliotecasmundo.shared.infrastructure.language.Z39Language;

public class QueryLanguageObjectMother {
    public static QueryLanguage commonQueryLanguageAkaZ3950() {
        return Z39Language.getInstance();
    }
}
