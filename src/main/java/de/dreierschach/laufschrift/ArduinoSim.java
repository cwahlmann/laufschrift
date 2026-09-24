package de.dreierschach.laufschrift;

public class ArduinoSim {
    private final boolean[] rows = new boolean[7];
    private final boolean[] cols = new boolean[7];
    private Runnable displayCallback = () -> {
    };

    public ArduinoSim() {
        for (int i=0; i<7; i++) {
            rows[i] = true;
            cols[i] = true;
        }
    }

    public ArduinoSim displayCallback(Runnable displayCallback) {
        this.displayCallback = displayCallback;
        return this;
    }

    public boolean col(int x) {
        return (x >= 0 && x <= 7 && cols[x]);
    }

    public void col(int x, boolean on) {
        if (x >= 0 && x <= 7) {
            cols[x] = on;
        }
        displayCallback.run();
    }

    public boolean row(int y) {
        return (y >= 0 && y <= 7 && rows[y]);
    }

    public void row(int y, boolean on) {
        if (y >= 0 && y <= 7) {
            rows[y] = on;
        }
        displayCallback.run();
    }

    public boolean hasLight(int x, int y) {
        return !col(x) && !row(y);
    }
}
