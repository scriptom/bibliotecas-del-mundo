package org.bibliotecasmundo.server.infrastructure.persistence.xml.models.book;

import lombok.*;

@Data
@NoArgsConstructor
public final class XmlBook {
    @NonNull private String title;
    @NonNull private String author;
}
