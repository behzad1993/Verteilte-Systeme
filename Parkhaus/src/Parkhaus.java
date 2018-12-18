import java.util.LinkedList;
import java.util.Queue;

public class Parkhaus {

    private int maxCapacity;
    private int freeCapacity;
    private Queue<Auto> queue;

    private int _nextCarId = 0;


    public Parkhaus(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.freeCapacity = maxCapacity;
        this.queue = new LinkedList<>();
    }

    public synchronized void enter(Auto auto) throws InterruptedException {
        int currentCarId = _nextCarId++;
        queue.add(auto);

//        if (freeCapacity == 0) {
//            System.out.println("CANT ENTER. FULL");
//            return;
//        }
        // TODO: WHILE
        try {
            if (_nextCarId == auto.getIdFromCar())
            wait();
        } catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
            return;
        }

        Auto actualCar = queue.poll();
//        actualCar.notify();
        notifyAll();
        if (actualCar.getIdFromCar() > 9) {
            System.out.println("Auto " + actualCar.getIdFromCar() + " fährt   EIN");
        } else {
            System.out.println("Auto " + actualCar.getIdFromCar() + " fährt    EIN");
        }
    }

    public synchronized void leave(Auto auto) {
        if (auto.getIdFromCar() > 9) {
            System.out.println("Auto " + auto.getIdFromCar() + " fährt          AUS");
        } else {
            System.out.println("Auto " + auto.getIdFromCar() + " fährt           AUS");
        }
    }
}

