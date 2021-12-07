package org.bibliotecasmundo.server.infrastructure.rmi;

import org.bibliotecasmundo.server.application.SearchBookUseCase;
import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.application.query.QueryLanguage;
import org.bibliotecasmundo.shared.application.query.UntranslatableQueryException;
import org.bibliotecasmundo.shared.domain.book.Book;
import org.bibliotecasmundo.shared.infrastructure.middleware.RmiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryServer extends UnicastRemoteObject implements RmiController {
    private final SearchBookUseCase searchBookUseCase;
    private final QueryLanguage queryLanguage;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public LibraryServer(SearchBookUseCase searchBookUseCase, QueryLanguage queryLanguage) throws RemoteException {
        this.searchBookUseCase = searchBookUseCase;
        this.queryLanguage = queryLanguage;
    }

    @Override
    public String queryBooks(String query) throws RemoteException {
        try {
            // Assume all queries come in z39lang
            Query translatedQuery = translateQuery(query);
            List<Book> resultados = searchBookUseCase.search(translatedQuery);
            logger.info("{} results found for query: {}", resultados.size(), query);
            logger.debug("Resultados: {}", resultados);
            return resultados.stream().map(book -> {
                // TODO: Use configuration values
                return "Libro " + book.getTitle() + "|" + book.getAuthor();
            }).collect(Collectors.joining("\n"));
        } catch (UntranslatableQueryException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    private Query translateQuery(String query) throws UntranslatableQueryException {
        return queryLanguage.translateFromCommonLanguage(query);
    }

    public void register(int port, String endpoint) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(endpoint, this);
    }
}
