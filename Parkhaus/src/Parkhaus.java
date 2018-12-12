public class Parkhaus {

    private int maxCapacity;
    private int freeCapacity;

    public Parkhaus(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.freeCapacity = maxCapacity;
    }

    public void enter(Auto auto) throws InterruptedException {
        System.out.println("Auto " + auto.getIdFromCar() + " fährt ein");
    }

    public void leave(Auto auto) {
        System.out.println("Auto " + auto.getIdFromCar() + " fährt aus");
    }

    public Boolean addCar() {
        if (this.freeCapacity > 0) {
            this.freeCapacity--;
            return true;
        }
        return false;
    }

    public Boolean removeCar() {
        if (this.freeCapacity < maxCapacity) {
            this.freeCapacity++;
            return true;
        }
        return false;
    }

    public int getmaxCapacity() {
        return maxCapacity;
    }

    public int getfreeCapacity() {
        return freeCapacity;
    }
}

