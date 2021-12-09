package org.bibliotecasmundo.client.application.xml.mapper;

import org.bibliotecasmundo.client.application.xml.modelos.libraryInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class xmlMapper {

    private String filename;

    public xmlMapper(String filename){
        this.filename = filename;
    }

    public libraryInfo[] readFile (){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try{
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // Parseo del XML
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(this.filename));

            doc.getDocumentElement().normalize();

            // get <biblioteca>
            NodeList list = doc.getElementsByTagName("biblioteca");

            //Para que el array de bibliotecas pueda ser dinamico, lo genero a del tamaño de la cantidad de elementos
            libraryInfo[] bibliotecas = new libraryInfo[list.getLength()];

            for (int biblio = 0; biblio < list.getLength(); biblio++) {

                Node node = list.item(biblio);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // Obtengo los attributos
                    String name = element.getAttribute("name");
                    String address = element.getAttribute("address");
                    String port = element.getAttribute("port");

                    bibliotecas[biblio] = new libraryInfo(name,address,port);

                }
            }
            return bibliotecas;
        }catch (  SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return new libraryInfo[0];
    }
}
