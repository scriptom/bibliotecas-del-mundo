package org.bibliotecasmundo.client.domain.library;

import lombok.Value;
import org.bibliotecasmundo.shared.domain.ValueObject;

@Value(staticConstructor = "create")
public class LibraryName implements ValueObject<String> {
    String name;

    @Override
    public String getValue() {
        return name;
    }
}
