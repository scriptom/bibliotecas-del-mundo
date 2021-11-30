package org.bibliotecasmundo.server;

import org.bibliotecasmundo.server.application.LibraryQueryLanguage;
import org.bibliotecasmundo.server.application.SearchBookUseCase;
import org.bibliotecasmundo.server.infrastructure.config.ServerConfigConstants;
import org.bibliotecasmundo.server.infrastructure.persistence.inmemory.InMemoryBookRepository;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.XmlBookRepository;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.mapper.SAXDeserializerHandler;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.mapper.XmlUnmarshaller;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.mapper.impl.LibrarySAXUnmarshaller;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.modelos.libro.XmlLibraryRoot;
import org.bibliotecasmundo.server.infrastructure.persistence.xml.modelos.libro.deserializacion.LibrarySAXDeserializerHandler;
import org.bibliotecasmundo.server.infrastructure.rmi.LibraryServer;
import org.bibliotecasmundo.server.infrastructure.service.LocalSearchBookSearchService;
import org.bibliotecasmundo.shared.application.query.QueryLanguage;
import org.bibliotecasmundo.shared.domain.book.BookRepository;
import org.bibliotecasmundo.shared.infrastructure.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.rmi.RemoteException;

public class LibraryApplication {
    private final static String SERVER_PROPERTIES_PATH = "app.server.properties";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final LibraryServer libraryServer;
    private final AppConfig appConfig;

    public LibraryApplication(LibraryServer libraryServer, AppConfig appConfig) {
        this.libraryServer = libraryServer;
        this.appConfig = appConfig;
    }

    /**
     * Entry point for Server Application. Handles bootstrapping of services as well as leaving a listener on the RMI registry.
     * @param args CLI arguments (unused)
     */
    public static void main(String[] args) {
        try {
            AppConfig config = AppConfig.createFromPropertiesFile(SERVER_PROPERTIES_PATH);
            BookRepository bookRepository = buildBookRepository(config);
            SearchBookUseCase searchBookUseCase = new LocalSearchBookSearchService(bookRepository);
            QueryLanguage queryLanguage = LibraryQueryLanguage.buildFromConfiguration(config);
            LibraryServer libraryServer = new LibraryServer(searchBookUseCase, queryLanguage);
            LibraryApplication application = new LibraryApplication(libraryServer, config);
            application.start();
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getMessage());
        }
    }

    public void start() throws RemoteException {
        logger.info("Starting library server");
        logger.debug("Configuration: {}", appConfig);
        final int port = Integer.parseInt(appConfig.getConfigParam(ServerConfigConstants.APP_PORT, "5000"));
        final String endpoint = appConfig.getConfigParam(ServerConfigConstants.APP_ENDPOINT, "BookService");
        libraryServer.register(port, endpoint);
        logger.info("Server started on port {}", port);
    }

    private static BookRepository buildBookRepository(AppConfig config) {
        String repositoryPath = config.getConfigParam(ServerConfigConstants.LIBRARY_XML_REPOSITORY_PATH);
        if (repositoryPath != null && !repositoryPath.isEmpty()) {
            SAXDeserializerHandler<XmlLibraryRoot> deserializerHandler = new LibrarySAXDeserializerHandler(config);
            XmlUnmarshaller<XmlLibraryRoot> unmarshaller = new LibrarySAXUnmarshaller(deserializerHandler);
            return new XmlBookRepository(unmarshaller, repositoryPath);
        }

        return new InMemoryBookRepository();
    }
}
