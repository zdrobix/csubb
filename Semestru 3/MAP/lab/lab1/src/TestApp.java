import java.util.Vector;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TestApp {

    @Test
    public void testAll() {
        try {
            this.testNumarComplex(); System.out.println("ok ");
        } catch (AssertionError e) {
            System.out.println("NumarComplex test failed. " + e.getMessage());
        }
        try {
            this.testValidate(); System.out.println("ok ");
        } catch (AssertionError e) {
            System.out.println("Validator test failed. " + e.getMessage());
        }
        try {
            this.testExpressionClear(); System.out.println("ok ");
        } catch (AssertionError e) {
            System.out.println("ExpressionClear test failed. " + e.getMessage());
        }
        try {
            this.testOperation(); System.out.println("ok ");
        } catch (AssertionError e) {
            System.out.println("Operation test failed. " + e.getMessage());
        }
        try {
            this.testExpressionCalc(); System.out.println("ok ");
        } catch (AssertionError e) {
            System.out.println("ExpressionCalc test failed. " + e.getMessage());
        }
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

        NumarComplex nr3 = NumarComplex.fromString("+ 2.0 - 3.0 * i");
        assertEquals(nr3.getIm(), -3);
        assertEquals(nr3.getRe(), 2);

        assertEquals(nr3.toString(), NumarComplex.fromString(nr3.toString()).toString());
    }

    @Test
    public void testValidate() {
        NumarComplex nr = new NumarComplex(0, 0);
        String expr1[] = {nr.toString(), "+"};
        String expr2[] = {nr.toString(), "+", "+", nr.toString()};
        String expr3[] = {nr.toString(), nr.toString(), "+", nr.toString()};
        String expr4[] = {nr.toString(), "+", nr.toString()};

        try {
            ValidateExpression.Validate(expr1, expr1.length); fail();
        } catch (Exception e) { assertTrue(true);}
        try {
            ValidateExpression.Validate(expr2, expr2.length); fail();
        } catch (Exception e) { assertTrue(true);}
        try {
            ValidateExpression.Validate(expr3, expr3.length); fail();
        } catch (Exception e) { assertTrue(true);}
        try {
            ValidateExpression.Validate(expr4, expr4.length); assertTrue(true);
        } catch (Exception e) { fail();}

        String expr5[] = {"+ 2.0 - 3.0 * i", "-", "+ + 2.0 + 3.0 * i"};
        String expr6[] = {"+ 2.0 - 3.0 * i", "-", "+ 2.0 + 0.0 + 3.0 * i"};
        String expr7[] = {"+ 2.0 - 3.0 * i", "-", "+ 2.0 + 3.0 * i + "};

        try {
            ValidateExpression.Validate(expr5, expr5.length); fail();
        } catch (Exception e) { assertTrue(true);}
        try {
            ValidateExpression.Validate(expr6, expr6.length); fail();
        } catch (Exception e) { assertTrue(true);}
        try {
            ValidateExpression.Validate(expr7, expr7.length); fail();
        } catch (Exception e) { assertTrue(true);}
    }

    public void testExpressionClear() {
        String expr1[] = {"+ 2.0 - 3.0 * i", "*", "+ 2.0 + 3.0 * i", "+", "+ 1.0 + 1.0 * i"};
        String expr2[] = {"+ 2.0 - 3.0 * i", "*", "+ 2.0 + 3.0 * i", "*", "+ 1.0 + 1.0 * i"};
        String invalid[] = {"+ +2.0 - 3.0 * i", "*", "+ 2.0 asd+ 3.0 * i", "*", "+ 1.0 + 1.0 * i"};

        Vector<Pair> result1 = new ExpressionClear(expr1).getRidOfMultiplicationsAndDivision();
        Vector<Pair> result2 = new ExpressionClear(expr2).getRidOfMultiplicationsAndDivision();

        assertEquals(result1.size(), 1);
        assertEquals(result2.size(), 1);

        try {
            new ExpressionClear(invalid).getRidOfMultiplicationsAndDivision();
            fail();
        } catch (Exception e) {assertTrue(true);};
    }

    public void testExpressionCalc() {
        NumarComplex nr1 = new NumarComplex(1, 1);
        NumarComplex nr2 = new NumarComplex(2, 2);
        NumarComplex nr3 = new NumarComplex(0, 0);

        String expr1[] = {nr1.toString(), "+", nr1.toString(), "+", nr3.toString()};
        String expr2[] = {nr3.toString(), "+", nr3.toString(), "+", nr3.toString()};
        String expr3[] = {nr2.toString(), "*", nr1.toString(), "+", nr1.toString(), "+", nr3.toString()};

        assertEquals(new ExpressionCalc(expr1).getExpressionResult().getRe(), 2);
        assertEquals(new ExpressionCalc(expr1).getExpressionResult().getIm(), 2);

        assertEquals(new ExpressionCalc(expr2).getExpressionResult().getRe(), 0);
        assertEquals(new ExpressionCalc(expr2).getExpressionResult().getIm(), 0);

        assertEquals(new ExpressionCalc(expr3).getExpressionResult().getRe(), 1);
        assertEquals(new ExpressionCalc(expr3).getExpressionResult().getIm(), 5);
    }

    public void testOperation() {
        Operation op1 = new Operation(new NumarComplex(10, 10), new NumarComplex(10, 10), Op.ADD);
        Operation op2 = new Operation(new NumarComplex(10, 10), new NumarComplex(10, 10), Op.SUBTRACT);
        Operation op3 = new Operation(new NumarComplex(10, 10), new NumarComplex(10, 10), Op.MULTIPLY);
        Operation op4 = new Operation(new NumarComplex(10, 10), new NumarComplex(10, 10), Op.DIVIDE);

        assertEquals(op1.getResult().getRe(), 20);
        assertEquals(op1.getResult().getIm(), 20);

        assertEquals(op2.getResult().getRe(), 0);
        assertEquals(op2.getResult().getIm(), 0);

        assertEquals(op3.getResult().getRe(), 0);
        assertEquals(op3.getResult().getIm(), 200);

        assertEquals(op4.getResult().getRe(), 1);
        assertEquals(op4.getResult().getIm(), 0);
    }
}
