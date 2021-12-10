package org.bibliotecasmundo.client.domain.library;

import lombok.Data;

@Data(staticConstructor = "create")
public class Library {
    private final LibraryName name;
    private final LibraryAddress address;
    private final LibraryPort port;
}
