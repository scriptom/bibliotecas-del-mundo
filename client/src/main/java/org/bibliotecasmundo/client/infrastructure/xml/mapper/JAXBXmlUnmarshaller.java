package org.bibliotecasmundo.client.infrastructure.xml.mapper;

import org.bibliotecasmundo.client.domain.library.Library;
import org.bibliotecasmundo.client.infrastructure.xml.models.LibraryListXml;
import org.bibliotecasmundo.client.infrastructure.xml.models.LibraryXml;
import org.bibliotecasmundo.shared.infrastructure.tools.EntityMapper;
import org.bibliotecasmundo.shared.infrastructure.tools.xml.XmlUnmarshaller;
import org.bibliotecasmundo.shared.infrastructure.tools.xml.XmlUnmarshallingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;

public class JAXBXmlUnmarshaller implements XmlUnmarshaller<List<Library>> {

    private final EntityMapper<Library, LibraryXml> entityMapper;

    public JAXBXmlUnmarshaller(EntityMapper<Library, LibraryXml> entityMapper) {
        this.entityMapper = entityMapper;
    }

    @Override
    public List<Library> fromXml(String xml) throws XmlUnmarshallingException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(LibraryListXml.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            LibraryListXml xmlLibraries = (LibraryListXml) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return entityMapper.mapList(xmlLibraries.getLibraries());
        } catch (JAXBException e) {
            throw new XmlUnmarshallingException(e);
        }
    }
}
