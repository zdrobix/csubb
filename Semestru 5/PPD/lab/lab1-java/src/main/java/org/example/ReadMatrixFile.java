package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReadMatrixFile {
    static class Data {
        public int N, M, k;
        public int[][] matrix, kernel;
    }

    public static Data Read(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            Data data = new Data();
            String line = br.readLine();
            data.N = Integer.parseInt(line.split(" ")[0]);
            data.M = Integer.parseInt(line.split(" ")[1]);
            data.k = Integer.parseInt(line.split(" ")[2]);
            data.matrix = new int[data.N][data.M];
            data.kernel = new int[data.k][data.k];
            int index = 0;
            while ((line = br.readLine()) != null) {
               var elems = line.split(" ");
               if (index < data.N) {
                   for (int i = 0; i < data.M; i++) {
                       data.matrix[index][i] = Integer.parseInt(elems[i]);
                   }
               } else {
                   for (int i = 0; i < data.k; i++) {
                       data.kernel[index - data.N][i] = Integer.parseInt(elems[i]);
                   }
               }
                index++;
            }
            return data;
        }
    }
}
