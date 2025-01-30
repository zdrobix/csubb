package repo;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.*;

public class RepositoryPersons  {
    private List<Person> Persons;
    private long ID;

    public RepositoryPersons() {
        this.ID = 1;
        this.LoadFromFile();
    }

    private void LoadFromFile() {
        try {
            var fileReader = new Scanner(new File("src\\input\\persons.csv"));
            this.Persons = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                    String[] input = fileReader.nextLine().split(",");
                    this.Persons.add(
                        new Person(Long.parseLong(input[0]), input[1], input[2], input[3], input[4],  input[5],  input[6],  input[7],  input[8])
                    );
                    if (ID == Long.parseLong(input[0])) 
                        ID ++;
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddPerson (Person person) {
        this.Persons.add(person);
        this.ID ++;
        try {
            var fileWriter = new FileWriter("src\\input\\persons.csv", true);
            fileWriter.write(String.join(",", person.GetParams()));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Person> GetPersons() {
        return this.Persons;
    }

    public long GetNewID() {
        return this.ID;
    }
}   
