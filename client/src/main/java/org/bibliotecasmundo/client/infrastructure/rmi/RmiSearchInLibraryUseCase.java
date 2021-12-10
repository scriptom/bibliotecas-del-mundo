package org.bibliotecasmundo.client.infrastructure.rmi;

import org.bibliotecasmundo.client.application.useCases.SearchInLibraryUseCase;
import org.bibliotecasmundo.client.domain.library.Library;
import org.bibliotecasmundo.shared.application.query.Language;
import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.application.query.QueryResponse;
import org.bibliotecasmundo.shared.application.query.UntranslatableQueryResponseException;
import org.bibliotecasmundo.shared.domain.book.Book;
import org.bibliotecasmundo.shared.infrastructure.middleware.RmiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

public class RmiSearchInLibraryUseCase implements SearchInLibraryUseCase {
    private final Logger logger = LoggerFactory.getLogger(RmiSearchInLibraryUseCase.class);
    @Override
    public List<Book> queryInLibrary(Query query, Library library) {
        try {
            RmiController controller = (RmiController) Naming.lookup(String.format("rmi://%s:%d/search", library.getAddress().getValue(), library.getPort().getValue()));
            logger.info("Searching {} in library {} on address {}", query.getQueryString(), library.getName().getValue(), library.getAddress().getValue());
            final String s = controller.queryBooks(query.translateToCommonLanguage());
            final List<Book> results = parseResponse(s, query.getLanguage());
            logger.info("Found {} results", results.size());
            return results;
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private List<Book> parseResponse(String s, Language language) {
        try {
            QueryResponse<Book> bookQueryResponse = language.translateResponseFromCommonLanguage(s);
            return bookQueryResponse.getResults();
        } catch (UntranslatableQueryResponseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
