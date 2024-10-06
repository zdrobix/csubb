import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class TestApp {

    public void testAll() {
        this.testNumarComplex();
    }

    @Test
    private void testNumarComplex() {
        NumarComplex nr = new NumarComplex(0, 0);
        assert(nr.getIm() == 0);
        assert(nr.getRe() == 0);
        assert(nr.getIm() == 0);

        NumarComplex nr2 = new NumarComplex(1, 1);
        assert(nr.add(nr2).getRe() == 1);
        assert(nr.add(nr2).getIm() == 1);

        assert(nr2.subtract(nr).getRe() == 1);
        assert(nr2.subtract(nr).getIm() == 1);

        assert(nr2.multiply(nr).getRe() == 0);
        assert(nr2.multiply(nr).getIm() == 0);

        assert(nr2.multiply(nr2.conjugate()).getRe() == 2);
        assert(nr2.multiply(nr2.conjugate()).getIm() == 0);
    }
}
