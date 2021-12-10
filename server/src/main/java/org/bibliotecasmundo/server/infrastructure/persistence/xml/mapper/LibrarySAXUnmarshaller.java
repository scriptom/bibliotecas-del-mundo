package org.bibliotecasmundo.server.infrastructure.persistence.xml.mapper;

import org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.XmlLibraryRoot;
import org.bibliotecasmundo.shared.infrastructure.tools.xml.XmlUnmarshaller;
import org.bibliotecasmundo.shared.infrastructure.tools.xml.XmlUnmarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LibrarySAXUnmarshaller implements XmlUnmarshaller<XmlLibraryRoot> {
    private final SAXDeserializerHandler<XmlLibraryRoot> deserializer;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public LibrarySAXUnmarshaller(SAXDeserializerHandler<XmlLibraryRoot> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public XmlLibraryRoot fromXml(String xml) throws XmlUnmarshallingException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        logger.debug("Deserializing: " + xml);
        logger.debug("Deserializer: " + deserializer);
        try (InputStream is = new ByteArrayInputStream(xml.getBytes())) {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(is, deserializer);
            return deserializer.getResult();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            logger.error("Error during deserialization" + e.getMessage(), e);
            throw new XmlUnmarshallingException(e);
        }
    }
}
