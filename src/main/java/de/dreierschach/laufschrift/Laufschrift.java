package de.dreierschach.laufschrift;


public class Laufschrift {
    static void main() {
        var sim = new ArduinoSim();
        var view = new View(sim);
        view.setVisible(true);
        new Thread(() -> run(sim)).start();
    }

    public static void run(ArduinoSim sim) {
        for (int count = 0; count < 500; count++) {
            for (int c = 0; c < 7; c++) {
                sim.col(c, false);
                for (int r = 0; r < 7; r++) {
                    sim.row(r, (c + r) % 2 > 0 );
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    return;
                }
                sim.col(c, true);
            }
        }
    }
}
