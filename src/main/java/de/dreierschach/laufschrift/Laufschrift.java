package de.dreierschach.laufschrift;


public class Laufschrift {
    static void main() {
        var sim = new ArduinoSim();
        var view = new View(sim);
        view.setVisible(true);
        new Thread(() -> run(sim)).start();
    }


    public static void run(ArduinoSim sim) {
        while (true) {
            var p = drawHex(sim, 'I', 0,0);
            drawHex(sim, 'i', p,0);
        }
    }

    public static int drawHex(ArduinoSim sim, char c, int x, int y) {
        var charCode = c - 42;
        var charPointer = charCode * 5;

        // würde nichts angezeigt? Dann beenden
        if (x < -4 || x >= 7 || y < -6 || y >= 7) {
            return x;
        }

        // merkt sich, ob überhaupt schon anzuzeigende Bits gefunden wurden
        boolean found = false;

        int col = 0; // zeigt auf die aktuelle Spalte der Anzeige

        for (int i = 0; i < 5; i++) { // iteriert durch die Bytes des Zeichens

            // würde Spalte nicht angezeigt? Dann nächste Spalte
            if (x+col <0 || x+col >= 7) {
                continue;
            }

            // aktuelles byte lesen
            var b = ZEICHEN[charPointer + i];

            // leere Spalte?
            if (b == 0xff) {
                // wenn schon mal was anzuzeigen war, dann hier beenden
                if (found) {
                    return x + col + 1; // und neue Position zurückgeben
                }
                // ansonsten "leeres" byte ignorieren
                continue;
            }

            found = true; // jetzt wurde was anzuzeigendes gefunden!

            sim.col(x+col, false); // Spalte aktivieren

            // Zeilen durchgehen
            for (int r = 0; r < 7; r++) {

                // nur anzeigen, wenn innerhalb des Displays
                if (y+r >=0 && y+r < 7) {
                    sim.row(y+r, (b & 1) > 0); // bit lesen und Zeile aktivieren
                }
                b = b >> 1; // bits schieben
            }

            delay(10); // fürs Auge

            // alles wieder deaktivieren
            for (int r = 0; r<7; r++) {sim.row(r, true);}
            sim.col(x+col, true);

            // nächste Spalte
            col++;
        }
        // neue Position zurückgegen
        return x + 6;
    }

    public static void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //----------------------------Ascii-Array--------------------------
    //5* 23-48 ist A-Z
    //5* 6-15 ist 0-9
    public static final int[] ZEICHEN = {
            // 'ä', 5x7px (auf Ascii-Zeichen '*')
            0x47, 0x3a, 0x3b, 0x02, 0x3f,
            // 'ö', 5x7px (auf Ascii-Zeichen '+')
            0x47, 0x3a, 0x3b, 0x3a, 0x47,
            // 'ü', 5x7px (auf Ascii-Zeichen ',')
            0x43, 0x3e, 0x3f, 0x3e, 0x43,
            // '45', 5x8px
            0xff, 0x77, 0x77, 0x77, 0xff,
            // '46', 5x8px
            0xff, 0xff, 0x3f, 0xff, 0xff,
            // '47', 5x8px
            0x3f, 0x4f, 0x77, 0x79, 0x7e,
            // '48', 5x8px
            0x41, 0x3a, 0x36, 0x2e, 0x41,
            // '49', 5x8px
            0xff, 0x3d, 0x00, 0x3f, 0xff,
            // '50', 5x8px
            0x3d, 0x1e, 0x2e, 0x36, 0x39,
            // '51', 5x8px
            0x5d, 0x3e, 0x36, 0x36, 0x49,
            // '52', 5x8px
            0x67, 0x6b, 0x6d, 0x00, 0x6f,
            // '53', 5x8px
            0x30, 0x36, 0x36, 0x36, 0x4e,
            // '54', 5x8px
            0x41, 0x36, 0x36, 0x36, 0x4d,
            // '55', 5x8px
            0x7e, 0x7e, 0x06, 0x7a, 0x7c,
            // '56', 5x8px
            0x49, 0x36, 0x36, 0x36, 0x49,
            // '57', 5x8px
            0x59, 0x36, 0x36, 0x36, 0x41,
            // '58', 5x8px
            0xff, 0xff, 0x6b, 0xff, 0xff,
            // '59', 5x8px
            0xff, 0x3f, 0x4b, 0xff, 0xff,
            // '60', 5x8px
            0xff, 0x77, 0x6b, 0x5d, 0x3e,
            // '61', 5x8px
            0x6b, 0x6b, 0x6b, 0x6b, 0xff,
            // '62', 5x8px
            0x3e, 0x5d, 0x6b, 0x77, 0xff,
            // '63', 5x8px
            0x7d, 0x7e, 0x2e, 0x76, 0x79,
            // '64', 5x8px
            0x49, 0x36, 0x2a, 0x36, 0x29,
            // 'A', 5x8px
            0x01, 0x76, 0x76, 0x76, 0x01,
            // 'B', 5x8px
            0x00, 0x36, 0x36, 0x36, 0x49,
            // 'C', 5x8px
            0x41, 0x3e, 0x3e, 0x3e, 0x5d,
            // 'D', 5x8px
            0x00, 0x3e, 0x3e, 0x3e, 0x41,
            // 'E', 5x8px
            0x00, 0x36, 0x36, 0x36, 0x3e,
            // 'F', 5x8px
            0x00, 0x76, 0x76, 0x76, 0x7e,
            // 'G', 5x8px
            0x41, 0x3e, 0x36, 0x36, 0x45,
            // 'H', 5x8px
            0x00, 0x77, 0x77, 0x77, 0x00,
            // 'I', 5x8px
            0xff, 0x3e, 0x00, 0x3e, 0xff,
            // 'J', 5x8px
            0x5f, 0x3e, 0x3e, 0x3e, 0x40,
            // 'K', 5x8px
            0x00, 0x77, 0x77, 0x6b, 0x1c,
            // 'L', 5x8px
            0x00, 0x3f, 0x3f, 0x3f, 0x3f,
            // 'M', 5x8px
            0x00, 0x7d, 0x7b, 0x7d, 0x00,
            // 'N', 5x8px
            0x00, 0x7b, 0x77, 0x6f, 0x00,
            // 'O', 5x8px
            0x41, 0x3e, 0x3e, 0x3e, 0x41,
            // 'P', 5x8px
            0x00, 0x76, 0x76, 0x76, 0x79,
            // 'Q', 5x8px
            0x41, 0x3e, 0x2e, 0x1e, 0x01,
            // 'R', 5x8px
            0x00, 0x76, 0x76, 0x76, 0x09,
            // 'S', 5x8px
            0x51, 0x36, 0x36, 0x36, 0x4d,
            // 'T', 5x8px
            0x7e, 0x7e, 0x00, 0x7e, 0x7e,
            // 'U', 5x8px
            0x40, 0x3f, 0x3f, 0x3f, 0x40,
            // 'V', 5x8px
            0x60, 0x5f, 0x3f, 0x5f, 0x60,
            // 'W', 5x8px
            0x00, 0x5f, 0x6f, 0x5f, 0x00,
            // 'X', 5x8px
            0x1c, 0x6b, 0x77, 0x6b, 0x1c,
            // 'Y', 5x8px
            0x78, 0x77, 0x0f, 0x77, 0x78,
            // 'Z', 5x8px
            0x1e, 0x2e, 0x36, 0x3a, 0x3c,
            // '91', 5x8px
            0x00, 0x3e, 0x3e, 0xff, 0xff,
            // '92', 5x8px
            0x7e, 0x79, 0x77, 0x4f, 0x3f,
            // '93', 5x8px
            0xff, 0xff, 0x3e, 0x3e, 0x00,
            // '94', 5x8px
            0x7b, 0x7d, 0x7e, 0x7d, 0x7b,
            // '95', 5x8px
            0x3f, 0x3f, 0x3f, 0x3f, 0xff,
            // '96', 5x8px
            0x7e, 0x7d, 0xff, 0xff, 0xff,
            // '97', 5x8px
            0x47, 0x3b, 0x3b, 0x03, 0x3f,
            // '98', 5x8px
            0x00, 0x3b, 0x3b, 0x47, 0xff,
            // '99', 5x8px
            0x47, 0x3b, 0x3b, 0x3b, 0xff,
            // '100', 5x8px
            0x47, 0x3b, 0x3b, 0x00, 0xff,
            // '101', 5x8px
            0x47, 0x2b, 0x2b, 0x37, 0xff,
            // '102', 5x8px
            0xff, 0x01, 0x76, 0x7d, 0xff,
            // '103', 5x8px
            0xff, 0x33, 0x2d, 0x2d, 0x43,
            // '104', 5x8px
            0x00, 0x7b, 0x7b, 0x07, 0xff,
            // '105', 5x8px
            0xff, 0xff, 0x02, 0xff, 0xff,
            // '106', 5x8px
            0x5f, 0x3f, 0x3f, 0x42, 0xff,
            // '107', 5x8px
            0x00, 0x6f, 0x57, 0x3b, 0xff,
            // '108', 5x8px
            0xff, 0x40, 0x3f, 0x3f, 0xff,
            // '109', 5x8px
            0x03, 0x7b, 0x67, 0x7b, 0x03,
            // '110', 5x8px
            0x03, 0x77, 0x7b, 0x7b, 0x03,
            // '111', 5x8px
            0x47, 0x3b, 0x3b, 0x3b, 0x47,
            // '112', 5x8px
            0x03, 0x6d, 0x6d, 0x73, 0xff,
            // '113', 5x8px
            0xff, 0x73, 0x6d, 0x6d, 0x03,
            // '114', 5x8px
            0x03, 0x77, 0x7b, 0x7b, 0xff,
            // '115', 5x8px
            0xff, 0x37, 0x2b, 0x5b, 0xff,
            // '116', 5x8px
            0xff, 0x7b, 0x00, 0x3b, 0xff,
            // '117', 5x8px
            0x43, 0x3f, 0x3f, 0x3f, 0x43,
            // '118', 5x8px
            0x63, 0x5f, 0x3f, 0x5f, 0x63,
            // '119', 5x8px
            0x43, 0x3f, 0x4f, 0x3f, 0x43,
            // '120', 5x8px
            0x3b, 0x57, 0x6f, 0x57, 0x3b,
            // '121', 5x8px
            0x7b, 0x77, 0x0f, 0x77, 0x7b,
            // '122', 5x8px
            0xff, 0x1b, 0x2b, 0x33, 0xff
    };
}
