package org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.deserialization;

import com.google.common.collect.ImmutableMap;
import org.bibliotecasmundo.server.infrastructure.config.ServerConfigConstants;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.mapper.SAXDeserializerHandler;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.XmlLibraryRoot;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.objectmother.XmlLibraryRootObjectMother;
import org.bibliotecasmundo.shared.infrastructure.config.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class LibrarySAXDeserializerHandlerTest {

    @Test
    @DisplayName("Deserializes a XML document with a single book, building a proper POJO")
    void testDeserializerReturnsCorrectInstance() {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<bibliotecas>\n" +
                "    <bibliotecaA>\n" +
                "        <book>\n" +
                "            <libro>Harry Potter</libro>\n" +
                "            <autor>J.K. Rowling</autor>\n" +
                "        </book>\n" +
                "    </bibliotecaA>\n" +
                "</bibliotecas>";
        SAXDeserializerHandler<XmlLibraryRoot> handler = new LibrarySAXDeserializerHandler(getConfig());
        parse(handler, xml);
        XmlLibraryRoot result = handler.getResult();
        final XmlLibraryRoot expected = XmlLibraryRootObjectMother.libraryRootWithSingleHarryPotterBook();
        assertEquals(expected.getLibraryInfo().getBooks().size(), result.getLibraryInfo().getBooks().size());
        assertEquals(expected.getLibraryInfo().getBooks().get(0).getAuthor(), result.getLibraryInfo().getBooks().get(0).getAuthor());
        assertEquals(expected.getLibraryInfo().getBooks().get(0).getTitle(), result.getLibraryInfo().getBooks().get(0).getTitle());
    }

    void parse(SAXDeserializerHandler<XmlLibraryRoot> handler, String xml) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(is, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    AppConfig getConfig() {
        return AppConfig.createFromMap(
                ImmutableMap.<String, String>builder()
                        .put(ServerConfigConstants.LIBRARY_XML_AUTHOR_TAG, "autor")
                        .put(ServerConfigConstants.LIBRARY_XML_TITLE_TAG, "libro")
                        .put(ServerConfigConstants.LIBRARY_XML_ROOT_TAG, "bibliotecaA")
                        .build()
        );
    }
}
