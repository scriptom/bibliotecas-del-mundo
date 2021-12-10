package org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public final class LibraryInfo {
    @NonNull private List<XmlBook> books;
}
