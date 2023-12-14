package RoundRobin;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import javax.swing.*;
import java.awt.*;

// Implementación de Round Robin
public class Sistema {
    public static final int MEMORY_MAX = 1000;
    public static final int QUANTUM = 10;

    private static AtomicInteger memoryCurr = new AtomicInteger(0);

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Round Robin");

        ventana.setLayout(new GridBagLayout());
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setPreferredSize(new Dimension(800,500));

        ventana.pack();
        ventana.setVisible(true);

        Queue<Proceso> waitQueue = new LinkedList<Proceso>();
        Queue<Proceso> readyQueue = new LinkedList<Proceso>();

        Semaphore mutWait = new Semaphore(1);
        Semaphore mutReady = new Semaphore(1);
        Semaphore mutMemory = new Semaphore(1);

        Generador gen = new Generador(waitQueue, mutWait);
        Administrador admin = new Administrador(mutWait, waitQueue, mutReady, readyQueue, mutMemory, memoryCurr, MEMORY_MAX);
        Ejecutador ejec = new Ejecutador(mutReady, readyQueue, mutMemory, memoryCurr, QUANTUM);

        gen.start();
        admin.start();
        ejec.start();

        // crear un panel por cada proceso que entra a la cola de listos y eliminarlo si sale
        while (true) {
            try {
                mutReady.acquire();
                mutReady.release();
            } catch (Exception e) {
                System.err.println("error en main while");
            }
        }
    }
}

