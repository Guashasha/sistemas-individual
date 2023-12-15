package RoundRobin;

import java.util.Queue;
import java.util.LinkedList;
import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame {
    private Queue<JPanel> bloques = new LinkedList<JPanel>();
    JPanel contenedor = new JPanel();

    public Ventana() {
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1100,300));

        this.pack();
        this.setVisible(true);
    }

    public void InsertarBloque(int size) {
        JPanel bloque = new JPanel();
        // bloque.add(new JLabel(String.valueOf(size)));
        bloque.setBackground(Color.blue);
        bloque.setPreferredSize(new Dimension(size, 90));

        this.add(bloque);
        bloques.add(bloque);
        this.pack();
    }

    public void QuitarBloque() {
        this.remove(bloques.poll());
        CambiarColor();
        this.pack();
    }

    public void RefrescarBloque() {
        JPanel b = bloques.poll();
        b.setBackground(Color.blue);
        bloques.offer(b);
        CambiarColor();
        this.pack();
    }

    public void CambiarColor() {
        bloques.peek().setBackground(Color.red);
        this.pack();
    }
}
