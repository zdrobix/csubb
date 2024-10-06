import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestApp {

    @org.junit.Test
    public void testAll() {
        this.testNumarComplex();
    }

    @Test
    public void testNumarComplex() {
        NumarComplex nr = new NumarComplex(0, 0);
        assertEquals(nr.getIm(), 0);
        assertEquals(nr.getRe(), 0);
        assertEquals(nr.getIm(), 0);

        NumarComplex nr2 = new NumarComplex(1, 1);
        assertEquals(nr.add(nr2).getRe(), 1);
        assertEquals(nr.add(nr2).getIm(), 1);

        assertEquals(nr2.subtract(nr).getRe(), 1);
        assertEquals(nr2.subtract(nr).getIm(), 1);

        assertEquals(nr2.multiply(nr).getRe(), 0);
        assertEquals(nr2.multiply(nr).getIm(), 0);

        assertEquals(nr2.multiply(nr2.conjugate()).getRe(), 2);
        assertEquals(nr2.multiply(nr2.conjugate()).getIm() , 0);
    }
}
