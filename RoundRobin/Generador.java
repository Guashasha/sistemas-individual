package RoundRobin;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

// Generador de procesos en intervalos aleatorios
public class Generador extends Thread {
    Random rand = new Random();
    Queue<Proceso> waitQueue;
    Semaphore mutex;

    public Generador(Queue<Proceso> waitQueue, Semaphore mutex) {
        this.waitQueue = waitQueue;
        this.mutex = mutex;
    }

    public void generar() {
        try {
            mutex.acquire();

            int time = rand.nextInt(100 - 5) + 5;
            int size = rand.nextInt(100 - 10) + 10;

            Proceso n = new Proceso(time, size);
            waitQueue.add(n);
            
            mutex.release();
        }
        catch (Exception e) {
            System.err.println("Error al generar proceso");
        }
    }

    @Override
    public void run() {
        Random rand = new Random();
        
        while(true) {
            try {
                sleep(300);
            } catch (Exception e) {
                System.err.println("error en run Generador");
            }
            generar();
        }
    }
}
