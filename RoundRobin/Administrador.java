package RoundRobin;

import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Administrador extends Thread {
    private Queue<Proceso> waitQueue;
    private Queue<Proceso> readyQueue;
    private Semaphore mutWait;
    private Semaphore mutReady;

    private Semaphore mutMemory;
    private int memoryMax;
    private AtomicInteger memoryCurr;

    public Administrador(Semaphore mutWait, Queue<Proceso> waitQueue, Semaphore mutReady, Queue<Proceso> readyQueue, Semaphore mutMemory, AtomicInteger memoryCurr, int memoryMax) {
        this.mutWait=mutWait;
        this.waitQueue=waitQueue;
        this.mutReady=mutReady;
        this.readyQueue=readyQueue;
        this.mutMemory=mutMemory;
        this.memoryMax=memoryMax;
        this.memoryCurr=memoryCurr;
    }

    public void run() {
        Proceso p;

        while (true) {
            try {
                mutWait.acquire();
                p = waitQueue.poll();
                mutWait.release();

            // mover de la cola de espera a la de listos
                while (p != null) {
                    if (p.getSize() + memoryCurr.get() <= memoryMax) {
                        mutMemory.acquire();

                        memoryCurr.addAndGet(p.getSize());
                        System.out.println("memoria: " + memoryCurr.get());

                        mutMemory.release();

                        mutReady.acquire();

                        readyQueue.offer(p);

                        mutReady.release();

                        p = null;
                    }
                }
            } catch (Exception e) {
                System.err.println("Error al conseguir mutex en administrador");
            }
        }
    }
}
