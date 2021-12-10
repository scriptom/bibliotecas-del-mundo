package org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.deserialization;

import org.bibliotecasmundo.server.infrastructure.config.ServerConfigConstants;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.mapper.SAXDeserializerHandler;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.LibraryInfo;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.XmlBook;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.XmlLibraryRoot;
import org.bibliotecasmundo.shared.infrastructure.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class LibrarySAXDeserializerHandler extends SAXDeserializerHandler<XmlLibraryRoot> {
    private static final int DEFAULT_LIBRARY_SIZE = 30;
    private static final String XML_ROOT_TAG = "bibliotecas";
    private static final String BOOK_ROOT_TAG = "book";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final StringBuffer buffer = new StringBuffer();
    private final AppConfig appConfig;
    private XmlLibraryRoot xmlLibraryRoot;
    private LibraryInfo libraryInfo;
    private List<XmlBook> libros;
    private XmlBook libro;

    public LibrarySAXDeserializerHandler(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        logger.debug("START ELEMENT: uri=" + uri + ", localName=" + localName + ", qName=" + qName + ", attributes.length=" + attributes.getLength());
        if (qName.equals(XML_ROOT_TAG)) {
            xmlLibraryRoot = new XmlLibraryRoot();
        }

        if (qName.equals(appConfig.getConfigParam(ServerConfigConstants.LIBRARY_XML_ROOT_TAG))) {
            libraryInfo = new LibraryInfo();
            libros = new ArrayList<>(DEFAULT_LIBRARY_SIZE);
        }

        if (qName.equals(appConfig.getConfigParam(ServerConfigConstants.LIBRARY_XML_TITLE_TAG))) {
            libro = new XmlBook();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(XML_ROOT_TAG)) {
            xmlLibraryRoot.setLibraryInfo(libraryInfo);
            logger.debug("FINALIZO DESERIALIZACION CON RESULTADO: " + xmlLibraryRoot);
        }

        if (qName.equals(appConfig.getConfigParam(ServerConfigConstants.LIBRARY_XML_ROOT_TAG))) {
            libraryInfo.setBooks(libros);
        }

        if (qName.equals(BOOK_ROOT_TAG)) {
            libros.add(libro);
        }

        if (qName.equals(appConfig.getConfigParam(ServerConfigConstants.LIBRARY_XML_TITLE_TAG))) {
            libro.setTitle(buffer.toString());
        }

        if (qName.equals(appConfig.getConfigParam(ServerConfigConstants.LIBRARY_XML_AUTHOR_TAG))) {
            libro.setAuthor(buffer.toString());
        }

        limpiarBuffer();
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String contenido = new String(ch, start, length);
        buffer.append(contenido.trim());
        logger.debug("CHARACTERS: " + buffer);
    }

    protected void limpiarBuffer() {
        buffer.setLength(0);
    }

    @Override
    public XmlLibraryRoot getResult() {
        return xmlLibraryRoot;
    }
}
