package org.bibliotecasmundo.shared.infrastructure.tools.xml;

public interface XmlUnmarshaller<T> {
    T fromXml(String xml) throws XmlUnmarshallingException;
}
