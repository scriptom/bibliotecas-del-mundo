package org.bibliotecasmundo.client.domain.library;

import lombok.Value;
import org.bibliotecasmundo.shared.domain.ValueObject;

@Value(staticConstructor = "create")
public class LibraryPort implements ValueObject<Integer> {
    Integer port;

    @Override
    public Integer getValue() {
        return port;
    }
}
