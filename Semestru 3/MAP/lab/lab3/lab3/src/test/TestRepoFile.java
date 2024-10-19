package test;

import domain.validators.UtilizatorValidator;
import jdk.jshell.execution.Util;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import repo.file.UtilizatorFile;
import domain.Utilizator;

import java.util.Arrays;

public class TestRepoFile {
    @Test
    public void testAll() {
        this.testCreate();
        this.testAddDelete();
    }

    @Test
    public void testCreate() {
        var repo = new UtilizatorFile("Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\input\\inputTest.txt", new UtilizatorValidator());
        assert(repo.findOne(100L).get().equals(new Utilizator("Alex", "Zdroba")));
        var user = repo.extractEntity(Arrays.asList("100", "Alex", "Zdroba"));
        assertEquals(user.getFirstName(), "Alex");
        assertEquals(user.getLastName(), "Zdroba");
        assertEquals(user.getId(), 100L);
    }

    @Test
    public void testAddDelete() {
        var repo = new UtilizatorFile("Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\input\\inputTest.txt", new UtilizatorValidator());

        var user1 = repo.extractEntity(Arrays.asList("200", "Mihai", "Mihai"));
        var user2 = repo.extractEntity(Arrays.asList("300", "George", "George"));

        repo.save(user1);
        assert(repo.findOne(200L).get().equals(user1));

        repo.save(user2);
        assert(repo.findOne(300L).get().equals(user2));

        assert(repo.delete(200L).get().equals(user1));
        assert(repo.delete(300L).get().equals(user2));
        //assertTrue(repo.findOne(200L).isEmpty());
        //assertTrue(repo.findOne(300L).isEmpty());
    }
}
