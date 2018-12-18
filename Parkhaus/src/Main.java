import java.util.Random;

public class Main {

    private static int CARS = 30;
    private static int ONE = 1;
    private static int MAXCAP = 100;
    private static int ANFAHRTMAX = 10000;
    private static int ANFAHRTMIN = 1000;
    private static int PARKENMAX = 5000;
    private static int PARKENMIN = 500;

    public static void main(String[] args) {
        Parkhaus parkhaus = new Parkhaus(MAXCAP);
        int cars;
        try {
            cars = Integer.parseInt(args[0]);
        } catch (Exception e) {
            cars = CARS;
        }

        for (int i = 0; i < cars; i++) {
            Random r = new Random();
            int anfahrt = r.nextInt((ANFAHRTMAX - ANFAHRTMIN) + ONE) + ANFAHRTMIN;
            int parkzeit = r.nextInt((PARKENMAX - PARKENMIN) + ONE) + PARKENMIN;
            new Auto(i, parkhaus, anfahrt, parkzeit).start();
        }

    }
}
