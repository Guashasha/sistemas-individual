package RoundRobin;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

// Generador de procesos en intervalos aleatorios
public class Generador extends Thread {
    private Random rand = new Random();
    private Queue<Proceso> waitQueue;
    private Semaphore mutWait;
    private Ventana ventana;

    public Generador(Queue<Proceso> waitQueue, Semaphore mutWait, Ventana ventana) {
        this.waitQueue = waitQueue;
        this.mutWait = mutWait;
        this.ventana = ventana;
    }

    public void generar() {
        try {
            mutWait.acquire();

            int time = rand.nextInt(2150 - 500) + 50;
            int size = rand.nextInt(130 - 50) + 50;

            Proceso n = new Proceso(time, size);
            waitQueue.add(n);
            
            mutWait.release();
        }
        catch (Exception e) {
            System.err.println("Error al generar proceso");
        }
    }

    @Override
    public void run() {
        int s = rand.nextInt(2100 - 400) + 400;
        
        while(true) {
            try {
                sleep(s);
            } catch (Exception e) {
                System.err.println("error en run Generador");
            }

            if (waitQueue.size() < 15) {
                generar();
            }
        }
    }
}
