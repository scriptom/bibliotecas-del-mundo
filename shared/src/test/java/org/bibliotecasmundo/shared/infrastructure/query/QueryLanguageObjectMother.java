package org.bibliotecasmundo.shared.infrastructure.query;

import org.bibliotecasmundo.shared.application.query.Language;
import org.bibliotecasmundo.shared.application.query.Z39Language;

public class QueryLanguageObjectMother {
    public static Language commonQueryLanguageAkaZ3950() {
        return Z39Language.getInstance();
    }
}
