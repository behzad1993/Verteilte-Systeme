public class Auto extends Thread {

    private final Parkhaus parkhaus;

    private final int anfahrt;
    private final int parkzeit;
    private final int id;


    public Auto(int id, Parkhaus parkhaus, int anfahrt, int parkzeit) {
        this.id = id;
        this.parkhaus = parkhaus;
        this.anfahrt = anfahrt;
        this.parkzeit = parkzeit;
    }

    public int getIdFromCar() {
        return id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(anfahrt);
            parkhaus.enter(this);
            Thread.sleep(parkzeit);
            parkhaus.leave(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}