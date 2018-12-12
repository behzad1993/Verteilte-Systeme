import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Parkhaus parkhaus = new Parkhaus(100);

        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            int anfahrt = r.nextInt((1000 - 100) + 1) + 100;
            int parkzeit = r.nextInt((500 - 100) + 1) + 100;
            new Auto(i, parkhaus, anfahrt, parkzeit).start();
        }

    }
}
