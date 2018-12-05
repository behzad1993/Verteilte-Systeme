package rmiexample;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI
 *
 * @author Behzad Karimi on 29.11.18
 */
public class Client {

    public static void main(String[] args) {

//        String host = (args.length < 1) ? null : args[0];

        try {
            Registry registry = LocateRegistry.getRegistry(1111);
            Hello helloStub = (Hello) registry.lookup("Hello");
            String response = helloStub.sayHello();

            System.out.println("response: " + response);
        } catch (RemoteException e) {
            System.err.println("Client RemoteException: " + e.toString());
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("Client NotBoundException: " + e.toString());
            e.printStackTrace();
        }
    }
}
