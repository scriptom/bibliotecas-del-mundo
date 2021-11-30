package org.bibliotecasmundo.server.infrastructure.persistence.xml.modelos.libro.objectmother;


import org.bibliotecasmundo.server.infrastructure.persistence.xml.modelos.libro.XmlLibraryRoot;

public class XmlLibraryRootObjectMother {
    public static XmlLibraryRoot libraryRootWithSingleHarryPotterBook() {
        final XmlLibraryRoot result = new XmlLibraryRoot();
        result.setLibraryInfo(LibraryInfoObjectMother.libraryInfoOfTestLibraryWithSingleHarryPotterBook());
        return result;
    }
}
