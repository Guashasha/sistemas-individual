package RoundRobin;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

// Implementación de Round Robin
public class Sistema {
    private int memoriaMax = 1000;
    private int memoriaAct = 1000;

    public static void main(String[] args) {
        // Cola de procesos que están listos para ejecutarse
        Queue<Proceso> readyQueue = new LinkedList<Proceso>();

        // Cola de procesos que están en espera
        // Si la memoria está llena
        Queue<Proceso> waitQueue = new LinkedList<Proceso>();

        Semaphore mutex = new Semaphore(1);
        Generador gen = new Generador(waitQueue, mutex);

        gen.start();

        while(true) {
            try {
                mutex.acquire();

                if(waitQueue.peek() != null)
                    System.out.println(waitQueue.poll());

                mutex.release();
            }
            catch (Exception e) {
                System.err.println("error en main: " + e);
            }
        }
    }
}

