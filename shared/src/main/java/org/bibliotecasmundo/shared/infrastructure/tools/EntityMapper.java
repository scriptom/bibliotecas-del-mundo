package org.bibliotecasmundo.shared.infrastructure.tools;

import java.util.List;
import java.util.stream.Collectors;

public interface EntityMapper<T, S> {
    T map(S source);
    default List<T> mapList(List<S> source) {
        return source.stream().map(this::map).collect(Collectors.toList());
    }
}
