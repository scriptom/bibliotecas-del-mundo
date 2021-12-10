package org.bibliotecasmundo.client.domain.library;

import lombok.Value;
import org.bibliotecasmundo.shared.domain.ValueObject;

@Value(staticConstructor = "create")
public class LibraryAddress implements ValueObject<String> {
    String address;

    @Override
    public String getValue() {
        return address;
    }
}
