package org.bibliotecasmundo.server.infrastructure.persistence.xml.modelos.libro.objectmother;

import org.bibliotecasmundo.server.infrastructure.persistence.xml.modelos.libro.LibraryInfo;

import java.util.Collections;

public class LibraryInfoObjectMother {
    public static LibraryInfo libraryInfoOfTestLibraryWithSingleHarryPotterBook() {
        LibraryInfo result = new LibraryInfo();
        result.setBooks(Collections.singletonList(XmlBookObjectMother.xmlBookOfHarryPotter()));
        return result;
    }
}
