package org.bibliotecasmundo.client.infrastructure.xml.mapper;

import org.bibliotecasmundo.client.domain.library.Library;
import org.bibliotecasmundo.client.domain.library.LibraryAddress;
import org.bibliotecasmundo.client.domain.library.LibraryName;
import org.bibliotecasmundo.client.domain.library.LibraryPort;
import org.bibliotecasmundo.client.infrastructure.xml.models.LibraryXml;
import org.bibliotecasmundo.shared.infrastructure.tools.EntityMapper;

public class LibraryXmlMapper implements EntityMapper<Library, LibraryXml> {
    @Override
    public Library map(LibraryXml source) {
        return Library.create(
                LibraryName.create(source.getName()),
                LibraryAddress.create(source.getAddress()),
                LibraryPort.create(source.getPort())
        );
    }
}
