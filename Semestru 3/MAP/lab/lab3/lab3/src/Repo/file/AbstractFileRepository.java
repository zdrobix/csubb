package repo.file;

import domain.Entity;
import domain.validators.Validator;
import repo.memory.InMemoryRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    String fileName;
    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
        loadData();

    }

    private void loadData() {
        try {
            var fileReader = new Scanner(new File(this.fileName));
            while (fileReader.hasNextLine()) {
                String input = fileReader.nextLine();
                super.save(
                        extractEntity(
                                Arrays.asList(
                                        input.split(";"))));
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract E extractEntity(List<String> attributes);

    protected abstract String createEntityAsString(E entity);

    @Override
    public E save(E entity) {
        var result = super.save(entity);
        if (result == null)
            this.writeToFile();
        return result;
    }

    @Override
    public E delete(ID id)
    {
        var result = super.delete(id);
        if (result != null)
            this.writeToFile();
        return result;
    }

    protected void writeToFile()  {
        try {
            var fileWriter = new FileWriter(this.fileName, false);
            for (var entity : super.findAll())
                fileWriter.write(
                    createEntityAsString(entity) + '\n');
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

