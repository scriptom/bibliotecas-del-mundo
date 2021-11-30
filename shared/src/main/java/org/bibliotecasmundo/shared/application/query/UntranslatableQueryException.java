package org.bibliotecasmundo.shared.application.query;

public class UntranslatableQueryException extends RuntimeException {
    public UntranslatableQueryException() {
    }

    public UntranslatableQueryException(String message) {
        super(message);
    }

    public UntranslatableQueryException(String message, QueryLanguage language) {
        super(message + "\nQueryLanguage=" + language.getCommonName());
    }
}
