package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReadMatrixFile {
    static String FileName = "input/data.txt";

    public static int[][] Run() throws IOException {
        List<int[]> rows = new ArrayList<int[]>();

        try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {
            String line = br.readLine();
            while (line != null) {
                String[] data = line.split(" ");
                int[] row = new int[data.length];
                for (int i = 0; i < data.length; i++) {
                    row[i] = Integer.parseInt(data[i]);
                }
                rows.add(row);
                line = br.readLine();
            }
        }

        int[][] Matrix = rows.toArray(new int[rows.size()][]);

        return Matrix;
    }
}
