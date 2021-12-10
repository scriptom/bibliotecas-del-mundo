package org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.objectmother;

import org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.LibraryInfo;

import java.util.Collections;

public class LibraryInfoObjectMother {
    public static LibraryInfo libraryInfoOfTestLibraryWithSingleHarryPotterBook() {
        LibraryInfo result = new LibraryInfo();
        result.setBooks(Collections.singletonList(XmlBookObjectMother.xmlBookOfHarryPotter()));
        return result;
    }
}
