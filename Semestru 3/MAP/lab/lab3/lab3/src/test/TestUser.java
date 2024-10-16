package test;

import domain.Utilizator;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestUser {
    @Test
    public void testAll() {
        this.testCreate();
        this.testEquals();
    }

    @Test
    public void testCreate() {
        var user1 = new Utilizator("Alex", "Zdroba");
        assertEquals(user1.getFirstName(), "Alex");
        assertEquals(user1.getLastName(), "Zdroba");
    }
    @Test
    public void testEquals() {
        var user1 = new Utilizator("Alex", "Zdroba");
        assertEquals(user1, user1);
        assertNotEquals(user1, null);
        assertNotEquals(user1, new Utilizator("Zdroba", "Alex"));
        assertEquals(new Utilizator("Alex", "Zdroba"), new Utilizator("Alex", "Zdroba"));
        assertEquals(new Utilizator("Alex", "Zdroba").hashCode(), new Utilizator("Alex", "Zdroba").hashCode());
        assertNotEquals(new Utilizator("Alex", "asdad").hashCode(), new Utilizator("asdsad", "Zdroba").hashCode());
    }
}
