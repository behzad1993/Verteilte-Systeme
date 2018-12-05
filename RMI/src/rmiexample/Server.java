package rmiexample;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMI
 *
 * @author Behzad Karimi on 29.11.18
 */
public class Server extends UnicastRemoteObject implements Hello {

    public Server() throws RemoteException {
        super();
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello, World!";
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            Hello helloStub = (Hello) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.getRegistry(1111);
            registry.bind("Hello", helloStub);

            System.out.println("Server ready");
        } catch (RemoteException e) {
            System.out.println("Server Remote Exeption: " + e.toString());
            e.printStackTrace();
            return;
        } catch (AlreadyBoundException e) {
            System.out.println("Server AlreadyBoundExeption: " + e.toString());
            e.printStackTrace();
            return;
        }
    }
}
