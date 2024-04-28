import java.util.*;
import java.io.*;
import java.lang.*;

public class GuitarHero {

    String[][] song = null;
    boolean[] thatB = null;
    String[] noteNames;

    public GuitarHero() {
        try {
            File files = new File("Guitar Song.txt");
            BufferedReader input = new BufferedReader(new FileReader(files));
            int row = 0;
            String str;

            int[][] fretPos = {{29, 24, 19, 14, 10, 5},
                    {28, 23, 18, 13, 9, 4},
                    {27, 22, 17, 12, 8, 3},
                    {26, 21, 16, 11, 7, 2},
                    {25, 20, 15, 10, 6, 1}};

            boolean first = true;
            while ((str = input.readLine()) != null) {
                String[] measures = str.split(",");

                if (first) {
                    setUpAnswer(measures);
                    first = false;
                }

                for (int x = 0; x < measures.length; x++) {
                    for (int y = 0; y < 6; y++) {
                        int fp = fretPos[row][y];
                        if (measures[x].charAt(y) == '*' || measures[x].charAt(y) == 'o') {
                            song[fp][x + 1] = "o";
                            if (row == 4 && y == 3 && measures[x].charAt(y) == '*')
                                thatB[x + 1] = true;
                        }
                    }
                }
                row++;
                
            }
            input.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
        play();
    }

    public void setUpAnswer(String[] measures) {
        song = new String[30][measures.length + 1];
        for (int r = 0; r < song.length; r++) {
            for (int c = 0; c < song[0].length; c++) {
                song[r][c] = "";
            }
        }

        noteNames = new String[]{"G#", "G", "F#", "F", "E", "D#", "D", "C#", "C", "B", "A#",
                "G#", "G", "F#", "F", "E", "D#", "D", "C#", "C", "B", "A#",
                "G#", "G", "F#", "F", "E"};

        for (int r = 1; r <= noteNames.length; r++)
            song[r][0] = noteNames[r - 1];

        for (int x = 1; x <= measures.length; x++)
            song[0][x] = "" + x;
        song[0][0] = "Measure";

        thatB = new boolean[measures.length + 1];
    }

    public void play() {
        for (int r = 0; r < song.length; r++) {
            for (int c = 0; c < song[0].length; c++) {
                System.out.print(song[r][c] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        GuitarHero apps = new GuitarHero();
    }
}