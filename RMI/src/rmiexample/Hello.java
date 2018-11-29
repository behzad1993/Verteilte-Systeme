package rmiexample;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI
 *
 * @author Behzad Karimi on 29.11.18
 */
public interface Hello extends Remote {
    String sayHello() throws RemoteException;
}
