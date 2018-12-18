import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.TreeMap;

public class TemperatureServer extends Thread{

    private byte[] buf = new byte[256];
    private DatagramSocket socket;
    private DatagramPacket packet;
    private boolean running;
    private CSVReader csvReader;


    public TemperatureServer(DatagramSocket socket, DatagramPacket packet, CSVReader csvReader) {
        this.socket = socket;
        this.packet = packet;
        this.csvReader = csvReader;
    }

    public void run() {
        running = true;

        while (running) {
            this.packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());
                LocalDate localDate = null;
                try {
                    localDate = LocalDate.parse(received.substring(0, 10));
                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                }

                TreeMap<LocalTime, Float> tempValuesFromDate = csvReader.getTempValuesFromDate(localDate);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream ous = new ObjectOutputStream(baos);
                ous.writeObject(tempValuesFromDate);
                byte[] sendingData = baos.toByteArray();
                baos.close();

                packet = new DatagramPacket(sendingData, sendingData.length, address, port);
                socket.send(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}