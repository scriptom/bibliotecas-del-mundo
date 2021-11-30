package org.bibliotecasmundo.server.application;

public class InvalidSearchException extends RuntimeException{
    public InvalidSearchException(String message, String queryString) {
        super(message + "\nQuery String: " + queryString);
    }
}
