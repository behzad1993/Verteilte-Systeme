import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UDPTest {
    TemperatureClient client;

    @BeforeEach
    public void setup(){
        new MainServer().start();
        client = new TemperatureClient();
    }

    @Test
    public void receiveAvailableDateWithOneClient() {
        LocalDate localDate = LocalDate.parse("2018-12-01");
        client.sendDate(localDate);
    }

    @Test
    public void receiveAvailableDateWithTwoClients() {
        LocalDate localDate = LocalDate.parse("2018-12-01");
        TemperatureClient temperatureClient1 = new TemperatureClient();
        TemperatureClient temperatureClient2 = new TemperatureClient();
        TemperatureClient temperatureClient3 = new TemperatureClient();
        temperatureClient1.sendDate(localDate);
        temperatureClient2.sendDate(localDate);
        temperatureClient3.sendDate(localDate);
    }

    @Test
    public void receiveNoAvailableDate() {
        LocalDate localDate = LocalDate.parse("2018-11-01");
        client.sendDate(localDate);
    }
}