package org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.objectmother;

import org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.XmlBook;

public class XmlBookObjectMother {

    public static XmlBook xmlBookOfHarryPotter() {
        final XmlBook result = new XmlBook();
        result.setAuthor("J.K. Rowling");
        result.setTitle("Harry Potter");
        return result;
    }
}
