package test;

import domain.validators.UtilizatorValidator;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import repo.memory.InMemoryRepository;
import domain.Utilizator;

public class TestRepoMemory {

    @Test
    public void testAll() {
        this.testCreate();
        this.testSave();
        this.testUpdate();
        this.testDelete();
    }

    @Test
    public void testCreate(){
        var repo = new InMemoryRepository<>(new UtilizatorValidator());
        var user1 = new Utilizator("Alex", "Zdroba");
        user1.setId(1L);
        repo.save(user1);
        assert(repo.findOne(user1.getId()).equals(user1));
        assertNotEquals(repo.findOne(user1.getId()), null);
        assertNotEquals(repo.findOne(2L), user1);
    }

    @Test
    public void testSave() {
        var repo = new InMemoryRepository<>(new UtilizatorValidator());
        var user1 = new Utilizator("Alex", "Zdroba");
        user1.setId(1L);
        try {
            repo.save(null); fail();
        } catch (Exception e) { assert(true);}
        try {
            repo.save(new Utilizator("Nu", "")); fail();
        } catch (Exception e) { assert(true);}
        try {
            repo.save(new Utilizator("", "asdadasdad")); fail();
        } catch (Exception e) { assert(true);}
        repo.save(user1);
        assert(repo.findOne(user1.getId()).equals(user1));
    }

    @Test
    public void testUpdate() {
        var repo = new InMemoryRepository<>(new UtilizatorValidator());
        var user1 = new Utilizator("Alex", "Zdroba");
        user1.setId(1L);
        try {
            repo.update(null); fail();
        } catch (Exception e) { assert(true);}
        try {
            repo.update(new Utilizator("asdasda", "")); fail();
        } catch (Exception e) { assert(true);}
        try {
            repo.update(new Utilizator("", "asdadasdad")); fail();
        } catch (Exception e) { assert(true);}
        repo.save(user1);
        user1.setFirstName("Alexandru");
        assert(repo.update(user1) == null);
    }

    @Test
    public void testDelete() {
        var repo = new InMemoryRepository<>(new UtilizatorValidator());
        var user1 = new Utilizator("Alex", "Zdroba");
        user1.setId(1L);
        try {
            repo.delete(null);fail();
        } catch (Exception e) {assert(true); }
        repo.save(user1);
        assert(repo.delete(user1.getId()) != null);
    }
}
