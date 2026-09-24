package de.dreierschach.laufschrift;

public class ArduinoSim {
    private final int[] memory = new int[1024];
    private final boolean[] rows = new boolean[7];
    private final boolean[] cols = new boolean[7];
    private Runnable displayCallback = () -> {
    };

    public ArduinoSim() {
        for (int a=0; a<FONT.length; a++) {
            memory[a] = FONT[a];
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

    public int mem(int adr) {
        return (adr >= 0 && adr <= memory.length) ? memory[adr] : 0;
    }

    public void mem(int adr, int value) {
        if (adr >= 0 && adr <= memory.length) {
            memory[adr] = value;
        }
    }

    public static final int[] FONT = {
            0b00111000,
            0b01000100,
            0b10000010,
            0b10000010,
            0b10000010,
            0b01000100,
            0b00111000,

            0b11111110,
            0b10000010,
            0b10000010,
            0b10000010,
            0b10000010,
            0b10000010,
            0b11111110,
    };
}
