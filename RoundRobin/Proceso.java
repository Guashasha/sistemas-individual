package RoundRobin;

public class Proceso extends Thread {
    private int size;
    private int time;

    public Proceso(int tiempo, int tamano) {
        this.time = tiempo;
        this.size = tamano;
    }

    public int getTamano() {
        return size;
    }

    public int getTiempo() {
        return time;
    }

    @Override
    public void run() {
        System.out.println("hola hilo");
        try {
            sleep(100);
        } 
        catch (Exception e) {
            System.err.println("Error en Proceso");
        }
    }
}
