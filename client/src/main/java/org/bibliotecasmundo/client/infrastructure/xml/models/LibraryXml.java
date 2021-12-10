package org.bibliotecasmundo.client.infrastructure.xml.models;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class LibraryXml {
    @XmlAttribute
    @Getter
    private String name;

    @XmlAttribute
    @Getter
    private String address;

    @XmlAttribute
    @Getter
    private Integer port;
}
