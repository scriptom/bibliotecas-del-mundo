package org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.objectmother;


import org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book.XmlLibraryRoot;

public class XmlLibraryRootObjectMother {
    public static XmlLibraryRoot libraryRootWithSingleHarryPotterBook() {
        final XmlLibraryRoot result = new XmlLibraryRoot();
        result.setLibraryInfo(LibraryInfoObjectMother.libraryInfoOfTestLibraryWithSingleHarryPotterBook());
        return result;
    }
}
