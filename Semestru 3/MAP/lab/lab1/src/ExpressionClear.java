import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
public class ExpressionCalculate {

    private String args[];

    public ExpressionCalculate(String args_[]) {
        this.args = args_;
    }

    private Vector<Map<NumarComplex, Op>> getRidOfMultiplicationsAndDivision () {
        Vector<Map<NumarComplex, Op>> newExpression = new Vector<> ();
        for ( int i = 1; i < args.length; i += 2) {
            if ( this.args[i].equals("+") || this.args[i].equals("-") ) {
                Map<NumarComplex, Op> elem = new HashMap<NumarComplex, Op>();
                elem.put(NumarComplex.fromString(this.args[i-1]), Operation.getOperation(this.args[i]));
                newExpression.add(elem);
            } else if ( this.args[i].equals("*") || this.args[i].equals("/") ) {
                NumarComplex result = NumarComplex.fromString(this.args[i - 1]);
                while ( i + 1 < args.length  && (this.args[i].equals("*") || this.args[i].equals("/"))) {
                    result = (new Operation(result, NumarComplex.fromString(this.args[i + 1]), Operation.getOperation(this.args[i])).getResult());
                    i += 2;
                }
                Map<NumarComplex, Op> elem = new HashMap<NumarComplex, Op>();
                if (i <this.args.length)
                    elem.put(result, Operation.getOperation(this.args[i]));
                else elem.put(result, null);
                newExpression.add(elem);
            }
        }
        return newExpression;
    }
}
