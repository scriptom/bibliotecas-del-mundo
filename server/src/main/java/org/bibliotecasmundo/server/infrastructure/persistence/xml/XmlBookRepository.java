package org.bibliotecasmundo.server.infrastructure.persistence.xml;

import org.bibliotecasmundo.shared.infrastructure.tools.xml.XmlUnmarshaller;
import org.bibliotecasmundo.shared.infrastructure.tools.xml.XmlUnmarshallingException;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.XmlLibraryRoot;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.XmlBook;
import org.bibliotecasmundo.shared.domain.book.Book;
import org.bibliotecasmundo.shared.domain.book.BookAuthor;
import org.bibliotecasmundo.shared.domain.book.BookRepository;
import org.bibliotecasmundo.shared.domain.book.BookTitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class XmlBookRepository implements BookRepository {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final XmlUnmarshaller<XmlLibraryRoot> unmarshaller;
    private final String filename;

    public XmlBookRepository(XmlUnmarshaller<XmlLibraryRoot> unmarshaller, String filename) {
        this.unmarshaller = unmarshaller;
        this.filename = filename;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        try {
            String xml = readyXmlRepository();
            XmlLibraryRoot xmlLibraryRoot = unmarshaller.fromXml(xml);
            return xmlLibraryRoot.getLibraryInfo().getBooks()
                    .stream().filter(xmlBook -> xmlBook.getAuthor().equals(author))
                    .map(this::toDomain).collect(Collectors.toList());
        } catch (IOException | XmlUnmarshallingException e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        try {
            String xml = readyXmlRepository();
            XmlLibraryRoot xmlLibraryRoot = unmarshaller.fromXml(xml);
            return xmlLibraryRoot.getLibraryInfo().getBooks()
                    .stream().filter(xmlBook -> xmlBook.getTitle().equals(title))
                    .findFirst().map(this::toDomain);
        } catch (IOException | XmlUnmarshallingException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    private synchronized String readyXmlRepository() throws IOException {
        InputStream is = getClass().getResourceAsStream(filename);
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    private Book toDomain(XmlBook xmlBook) {
        return Book.create(BookTitle.create(xmlBook.getTitle()), BookAuthor.create(xmlBook.getAuthor()));
    }
}
