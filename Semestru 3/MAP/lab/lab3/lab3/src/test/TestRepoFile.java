package test;

import domain.Prietenie;
import domain.validators.PrietenieValidator;
import domain.validators.UtilizatorValidator;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import repo.file.PrietenieFile;
import repo.file.UtilizatorFile;
import domain.Utilizator;
import service.Service;
import service.ServiceFile;

import java.time.LocalDate;
import java.util.Arrays;

public class TestRepoFile {
    @Test
    public void testAll() {
        this.testCreate();
        this.testAddDelete();
        this.testCommunityAnalysis();
    }

    @Test
    public void testCreate() {
        var repo = new UtilizatorFile("Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\input\\inputTest.txt", new UtilizatorValidator());
        assert(repo.findOne(1L).get().equals(new Utilizator("Alex", "Zdroba")));
        var user = repo.extractEntity(Arrays.asList("1", "Alex", "Zdroba"));
        assertEquals(user.getFirstName(), "Alex");
        assertEquals(user.getLastName(), "Zdroba");
        assertEquals(user.getId(), 1L);
    }

    @Test
    public void testAddDelete() {
        var repo = new UtilizatorFile("Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\input\\inputTest.txt", new UtilizatorValidator());

        var user1 = repo.extractEntity(Arrays.asList("2", "Mihai", "Mihai"));
        var user2 = repo.extractEntity(Arrays.asList("3", "George", "George"));

        repo.save(user1);
        assert(repo.findOne(2L).get().equals(user1));

        repo.save(user2);
        assert(repo.findOne(3L).get().equals(user2));

        assert(repo.delete(2L).get().equals(user1));
        assert(repo.delete(3L).get().equals(user2));
        assertTrue(repo.findOne(2L).isEmpty());
        assertTrue(repo.findOne(3L).isEmpty());
    }

    @Test
    public void testCommunityAnalysis() {
        var repo = new UtilizatorFile("Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\input\\inputTest.txt", new UtilizatorValidator());
        var repoFriends = new PrietenieFile("Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\input\\inputTestFriends.txt", new PrietenieValidator());

        var user1 = repo.extractEntity(Arrays.asList("2", "Mihai", "Mihai"));
        var user2 = repo.extractEntity(Arrays.asList("3", "George", "George"));
        var user3 = repo.extractEntity(Arrays.asList("4", "Marius", "Ximo"));
        var user4 = repo.extractEntity(Arrays.asList("5", "Costica", "Solut"));
        var user5 = repo.extractEntity(Arrays.asList("6", "Marianovici", "Obox"));
        var user6 = repo.extractEntity(Arrays.asList("7", "Pantelimon", "Vlad"));

        repo.save(user1);
        repo.save(user2);
        repo.save(user3);
        repo.save(user4);
        repo.save(user5);
        repo.save(user6);

        //comunitatea Alex, Mihai, George - 3 oameni
        repoFriends.save(new Prietenie(1L, 2L, LocalDate.now()));
        repoFriends.save(new Prietenie(1L, 3L, LocalDate.now()));

        //comuniteatea Marius, Costica, Marianovici, Pantelimon - 4 oameni
        repoFriends.save(new Prietenie(4L, 5L, LocalDate.now()));
        repoFriends.save(new Prietenie(4L, 6L, LocalDate.now()));
        repoFriends.save(new Prietenie(4L, 7L, LocalDate.now()));
        var service = new ServiceFile(repo, repoFriends);

        assert(service.numberOfCommunities() == 2);

        var user7 = repo.extractEntity(Arrays.asList("8", "Rusu", "M"));
        repo.save(user7);
        assert(service.numberOfCommunities() == 3);

        var largestCommunity = service.largestCommunity();
        assert(largestCommunity.containsAll(Arrays.asList(4L, 5L, 6L, 7L)));
        assert(!largestCommunity.containsAll(Arrays.asList(1L, 8L)));

        for  ( Long i = 2L; i < 9L; i ++ )
            repo.delete(i);
    }
}
