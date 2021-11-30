package org.bibliotecasmundo.server.infrastructure.persistence.xml.modelos.libro;

import lombok.*;

@Data
@NoArgsConstructor
public final class XmlBook {
    @NonNull private String title;
    @NonNull private String author;
}
