package org.bibliotecasmundo.client.infrastructure.xml.models;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "bibliotecas")
public class LibraryListXml {
    @XmlElement(name = "biblioteca")
    @Getter
    private List<LibraryXml> libraries;
}
