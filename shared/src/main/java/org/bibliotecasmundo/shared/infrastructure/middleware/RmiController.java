package org.bibliotecasmundo.shared.infrastructure.middleware;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiController extends Remote {
    String queryBooks(String query) throws RemoteException;
}
