import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class UDPClient {

    private static int SERVERPORT = 3333;
    private static String IPADRESS = "localhost";

    public static void main(String[] args) throws IOException {

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(IPADRESS);

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        String sentence = inFromUser.readLine();
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, SERVERPORT);
        clientSocket.send(sendPacket);

        clientSocket.setSoTimeout(1000);

        while (true) {        // recieve data until timeout
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                String modifiedSentence = new String(receivePacket.getData());
                System.out.println("FROM SERVER:" + modifiedSentence);
            } catch (SocketTimeoutException e) {
                // timeout exception.
                System.out.println("Timeout reached!!! " + e);
            } catch (SocketException e) {

            }
            clientSocket.close();
        }
    }
}
