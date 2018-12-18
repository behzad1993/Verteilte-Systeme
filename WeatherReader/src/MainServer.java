import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class MainServer extends Thread {

    private DatagramSocket socket;
    private CSVReader csvReader;
    private byte[] buf = new byte[256];

    public MainServer() {
        csvReader = CSVReader.getInstance();
        try {
            this.socket = new DatagramSocket(4445);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                new Thread(new TemperatureServer(socket, packet, csvReader)).start();
            } catch (OutOfMemoryError e) {
                // TODO: building too many threads.
            }
        }
    }

    public static void main(String[] args) {
        new MainServer().start();
        System.out.println("Server started!");
    }
}


