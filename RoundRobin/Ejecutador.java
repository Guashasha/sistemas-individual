package RoundRobin;

import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Ejecutador extends Thread {
    private Semaphore mutReady;
    private Queue<Proceso> readyQueue;
    private Semaphore mutMemory;
    private AtomicInteger memoryCurr;
    private int quantum;

    public Ejecutador(Semaphore mutReady, Queue<Proceso> readys, Semaphore mutMemory, AtomicInteger memory, int quantum) {
        this.mutReady = mutReady;
        this.readyQueue = readys;
        this.mutMemory = mutMemory;
        this.memoryCurr = memory;
        this.quantum = quantum;
    }

    @Override
    public void run() {
        while (true) {
            try {
                mutReady.acquire();

                Proceso proc = readyQueue.peek();

                mutReady.release();

                if (proc != null) {
                    proc.setTime(proc.getTime() - quantum);
                    sleep(quantum);

                    if (proc.getTime() < 1) {
                        mutReady.acquire();
                        readyQueue.poll();
                        mutReady.release();

                        mutMemory.acquire();
                        memoryCurr.addAndGet(-proc.getSize());
                        mutMemory.release();
                    }
                    else {
                        readyQueue.offer(readyQueue.poll());
                    }

                    proc = null;
                }
            } catch (Exception e) {
                System.out.println("Error al dormir en ejecutador");
            }
        }
    }
}
