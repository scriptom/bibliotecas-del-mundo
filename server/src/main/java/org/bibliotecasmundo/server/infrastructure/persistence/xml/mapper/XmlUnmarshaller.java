package org.bibliotecasmundo.server.infrastructure.persistence.xml.mapper;

public interface XmlUnmarshaller<T> {
    T fromXml(String xml) throws XmlUnmarshallingException;
}
