package RoundRobin;

public class Proceso extends Thread {
    private int size;
    private int time;

    public Proceso(int tiempo, int tamano) {
        this.time = tiempo;
        this.size = tamano;
    }

    public int getSize() {
        return size;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    public int elapse(int ms) {
        this.time = time - ms;
        return time;
    }

    @Override
    public void run() {
        System.out.println("hola hilo");
    }
}
