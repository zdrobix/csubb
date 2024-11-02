package repo.file;

import domain.Entity;
import domain.validators.Validator;
import repo.memory.InMemoryRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    public Optional<E> save(E entity) {
        var result = super.save(entity);
        if (result == null) {
            try {
                this.writeToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Optional<E> delete(ID id)
    {
        var result = super.delete(id);
        if (result != null) {
            try {
                this.writeToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected void writeToFile() throws IOException {
        var fileWriter = new FileWriter(this.fileName, false);
        super.findAll().forEach(entity -> {
            try {
                fileWriter.write(createEntityAsString(entity) + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileWriter.close();
    }
}

