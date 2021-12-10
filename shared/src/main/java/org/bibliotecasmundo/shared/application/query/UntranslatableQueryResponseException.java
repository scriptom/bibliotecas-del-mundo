package org.bibliotecasmundo.shared.application.query;

public class UntranslatableQueryResponseException extends Exception {
    public UntranslatableQueryResponseException() {
    }

    public UntranslatableQueryResponseException(String message) {
        super(message);
    }

    public UntranslatableQueryResponseException(String message, Language language) {
        super(message + "\nQueryLanguage=" + language.getCommonName());
    }

}
