import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TemperatureClient {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;
    private byte[] buffer = new byte[1024];

    public TemperatureClient() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public TreeMap<LocalTime, Float> sendDate(LocalDate localDate) {
//        String received = null;
//        HashMap <LocalTime, Float> temperatures = null;
        TreeMap<LocalTime, Float> temperatures = null;
        byte b = 0;
        try {
            buf = localDate.toString().getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            byte[] receivedData = packet.getData();

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(receivedData));
            temperatures = (TreeMap<LocalTime, Float>) ois.readObject();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        printResult(temperatures);
        return  temperatures;
    }


    public Optional<Map.Entry<LocalTime, Float>> getMaxTemp(TreeMap<LocalTime, Float> localTimeFloatHashMap) {

        Comparator<? super Map.Entry<LocalTime, Float>> maxTempComparator = Comparator.comparing(Map.Entry::getValue);

        Optional<Map.Entry<LocalTime, Float>> maxTemp = localTimeFloatHashMap.entrySet()
                .stream().max(maxTempComparator);

        return maxTemp;
    }

    public Optional<Map.Entry<LocalTime, Float>> getMinTemp(TreeMap<LocalTime, Float> localTimeFloatHashMap) {

        Comparator<? super Map.Entry<LocalTime, Float>> minTempComparator = Comparator.comparing(Map.Entry::getValue);

        Optional<Map.Entry<LocalTime, Float>> minTemp = localTimeFloatHashMap.entrySet()
                .stream().min(minTempComparator);

        return minTemp;
    }


    private void printResult(TreeMap<LocalTime, Float> localTimeFloatHashMap) {

        if (localTimeFloatHashMap == null) {
            System.out.println("No data available for this date.");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        String degr = "°C";
        for (LocalTime localTime : localTimeFloatHashMap.keySet()) {
            stringBuilder.append(localTime);
            stringBuilder.append(" ");
            Float temperature = localTimeFloatHashMap.get(localTime);
            stringBuilder.append(String.format("%.2f", temperature));
            stringBuilder.append(degr);

            stringBuilder.append("\n");
        }
        stringBuilder.append("\n");

        stringBuilder.append("Max temperature is ");
        Optional<Map.Entry<LocalTime, Float>> maxTemp = getMaxTemp(localTimeFloatHashMap);
        stringBuilder.append(maxTemp.get().getValue());
        stringBuilder.append(degr);
        stringBuilder.append(" at ");
        stringBuilder.append(maxTemp.get().getKey());
        stringBuilder.append("\n");

        stringBuilder.append("Min temperature is ");
        Optional<Map.Entry<LocalTime, Float>> minTemp = getMinTemp(localTimeFloatHashMap);
        stringBuilder.append(minTemp.get().getValue());
        stringBuilder.append(degr);
        stringBuilder.append(" at ");
        stringBuilder.append(minTemp.get().getKey());
        stringBuilder.append("\n");

        float average = calcAverageTemp(localTimeFloatHashMap.entrySet());
        stringBuilder.append("Average of the temperature is ");
        String averageString = String.format("%.2f", average);
        stringBuilder.append(averageString);
        stringBuilder.append(degr);

        String result = stringBuilder.toString();

        System.out.println(result);
    }

    private float calcAverageTemp(Set<Map.Entry<LocalTime, Float>> entrySet) {

        float sum = 0;
        for (Map.Entry<LocalTime, Float> localTimeFloatEntry : entrySet) {
            sum += localTimeFloatEntry.getValue();
        }
        return sum/entrySet.size();
    }
}