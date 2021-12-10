package org.bibliotecasmundo.server.infrastructure.rmi;

import org.bibliotecasmundo.server.application.SearchBookUseCase;
import org.bibliotecasmundo.shared.application.query.*;
import org.bibliotecasmundo.shared.domain.book.Book;
import org.bibliotecasmundo.shared.infrastructure.middleware.RmiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryServer extends UnicastRemoteObject implements RmiController {
    private final SearchBookUseCase searchBookUseCase;
    private final Language language;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public LibraryServer(SearchBookUseCase searchBookUseCase, Language language) throws RemoteException {
        this.searchBookUseCase = searchBookUseCase;
        this.language = language;
    }

    @Override
    public String queryBooks(String query) throws RemoteException {
        try {
            // Assume all queries come in z39lang
            Query translatedQuery = translateQuery(query);
            logger.info("Received query: {} from host {}", translatedQuery.getQueryString(), getClientHost() );
            List<Book> resultados = searchBookUseCase.search(translatedQuery);
            logger.info("{} results found for query: {}", resultados.size(), translatedQuery.getQueryString());
            logger.debug("Resultados: {}", resultados);
            String responseString = resultados.stream().map(book ->
                    language.getParameterName(QueryParameter.RESPONSE) + " " + book.getTitle().getValue() + "|" + book.getAuthor().getValue()).collect(Collectors.joining("\n"));
            QueryResponse<Book> response = new BookQueryResponse(responseString, language);
            return language.translateResponseToCommonLanguage(response);
        } catch (UntranslatableQueryException | UntranslatableQueryResponseException | ServerNotActiveException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    private Query translateQuery(String query) throws UntranslatableQueryException {
        return language.translateFromCommonLanguage(query);
    }

    public void register(int port, String endpoint) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(endpoint, this);
    }
}
