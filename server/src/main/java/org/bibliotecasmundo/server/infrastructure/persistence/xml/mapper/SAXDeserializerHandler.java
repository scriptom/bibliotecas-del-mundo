package org.bibliotecasmundo.server.infrastructure.persistence.xml.mapper;

import org.xml.sax.helpers.DefaultHandler;

public abstract class SAXDeserializerHandler<T> extends DefaultHandler {
    abstract public T getResult();
}
