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
    private Ventana ventana;

    public Ejecutador(Semaphore mutReady, Queue<Proceso> readys, Semaphore mutMemory, AtomicInteger memory, int quantum, Ventana ventana) {
        this.mutReady = mutReady;
        this.readyQueue = readys;
        this.mutMemory = mutMemory;
        this.memoryCurr = memory;
        this.quantum = quantum;
        this.ventana = ventana;
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
                        ventana.QuitarBloque();
                        mutReady.acquire();
                        readyQueue.poll();
                        mutReady.release();

                        mutMemory.acquire();
                        memoryCurr.addAndGet(-proc.getSize());
                        mutMemory.release();
                    }
                    else {
                        ventana.RefrescarBloque();
                        readyQueue.offer(readyQueue.poll());
                    }

                    proc = null;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
