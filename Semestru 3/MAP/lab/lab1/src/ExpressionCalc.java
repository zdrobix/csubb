import java.util.Vector;
public class ExpressionCalc {
    private final Vector<Pair> cleanExpression;

    public ExpressionCalc(String args[]) {
        this.cleanExpression = new ExpressionClear(args).getRidOfMultiplicationsAndDivision();
    }

    public NumarComplex getExpressionResult() {
        NumarComplex result = this.cleanExpression.getFirst().getNumar();
        int i = 1;
        while (i < this.cleanExpression.size()) {
            NumarComplex nr = this.cleanExpression.get(i).getNumar();
            Op op = this.cleanExpression.get(i - 1).getOperation();
            result = new Operation(result, nr, op).getResult();
            i++;
        }
        return result;
    }
}
