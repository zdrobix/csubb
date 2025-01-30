package repo;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.*;

public class RepositoryNeeds {
    private List<Need> Needs;
    private long ID;

    public RepositoryNeeds() {
        this.ID = 1;
        this.LoadFromFile();
    }

    private void LoadFromFile() {
        try {
            var fileReader = new Scanner(new File("src\\input\\needs.csv"));
            this.Needs = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                    String[] input = fileReader.nextLine().split(",");
                    this.Needs.add(
                        new Need(Long.parseLong(input[0]), input[1], input[2], LocalDateTime.parse(input[3]), Long.parseLong(input[4]),  Long.parseLong(input[5]),  input[6])
                    );
                    if (this.ID == Long.parseLong(input[0]))
                        this.ID ++;
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddNeed (Need need) {
        this.Needs.add(need);
        try {
            var fileWriter = new FileWriter("src\\input\\needs.csv", true);
            fileWriter.write(String.join(",", need.GetParams()));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Need> GetNeeds() {
        return this.Needs;
    }

    public long GetNewID() {
        return this.ID;
    }
}   
