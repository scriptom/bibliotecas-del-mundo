package org.bibliotecasmundo.shared.infrastructure.middleware;

import org.bibliotecasmundo.shared.domain.book.Book;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiController extends Remote {
    List<Book> queryBooks(String query) throws RemoteException;
}
