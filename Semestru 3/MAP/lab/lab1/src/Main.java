public class Main {
    public static void main(String[] args) {

        NumarComplex nr1 = NumarComplex.fromString("+ 2.0 + 3.0 * i");
        NumarComplex nr2 = NumarComplex.fromString("- 2.0 - 3.0 * i");
        NumarComplex nr3 = NumarComplex.fromString("+ 2.0 - 3.0 * i");
        NumarComplex nr4 = NumarComplex.fromString("- 2.0 + 3.0 * i");

        String expr[] = {nr1.toString(), "+", nr2.toString(), "+", nr3.toString(), "+", nr4.toString()};
        try {
            ValidateExpression.Validate(expr, expr.length);
        } catch (Exception e) { System.out.println(e.getMessage()); }

    }
}