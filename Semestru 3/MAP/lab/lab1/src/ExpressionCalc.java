import java.util.Vector;
public class ExpressionCalc {
    private Vector<Pair> cleanExpression;

    public ExpressionCalc(String args[]) {
        try {
            this.cleanExpression = new ExpressionClear(args).getRidOfMultiplicationsAndDivision();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public NumarComplex getExpressionResult() {
        //Primeste un vector cu perechi de numere complexe si operatii, si calculeaza rezultatul expresiei.
        if (cleanExpression.isEmpty())
            return new NumarComplex(0, 0);
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
