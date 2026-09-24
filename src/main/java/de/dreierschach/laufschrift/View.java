package de.dreierschach.laufschrift;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class View extends JFrame {
    private final MainCanvas mainCanvas;

    public View(ArduinoSim arduinoSim) {
        super("Laufschrift");
        setSize(1024, 768);
        this.mainCanvas = new MainCanvas(arduinoSim);
        this.add(mainCanvas);
        arduinoSim.displayCallback(this::repaint);
        this.addComponentListener(
                new ComponentListener() {
                    @Override
                    public void componentResized(ComponentEvent componentEvent) {
                        mainCanvas.repaint();
                    }

                    @Override
                    public void componentMoved(ComponentEvent componentEvent) {
                    }

                    @Override
                    public void componentShown(ComponentEvent componentEvent) {
                    }

                    @Override
                    public void componentHidden(ComponentEvent componentEvent) {
                    }
                }
        );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static class MainCanvas extends JPanel {
        private final ArduinoSim arduinoSim;
        private int[][] buffer = new int[7][7];

        public MainCanvas(ArduinoSim arduinoSim) {
            this.arduinoSim = arduinoSim;
            super(false);
            grabFocus();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            var g2 = (Graphics2D) g;
            g2.setBackground(Color.BLACK);
            g2.clearRect(0, 0, getWidth(), getHeight());
            var dx = g.getClipBounds().width / 7;
            var dy = g.getClipBounds().height / 7;
            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 7; x++) {
                    if (arduinoSim.hasLight(x, y)) {
                        buffer[y][x] = 7;
                    } else {
                        if (buffer[y][x] > 0) {
                            buffer[y][x]--;
                        }
                    }
                    var color = new Color(buffer[y][x]*32, 0, 0);
                    g2.setColor(color);
                    g2.fillOval(x * dx, y * dy, dx,  dy);
                }
            }
        }
    }
}
