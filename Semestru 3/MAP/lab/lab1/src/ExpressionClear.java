import java.util.Vector;
public class ExpressionClear {
    private String args[];

    public ExpressionClear(String args_[]) {
        this.args = args_;
    }

    public Vector<Pair> getRidOfMultiplicationsAndDivision () {
        //Ordinea ef. op.
        //Scapa de orice inseamna inmultire/impartire.
        //Realizeaza inmultirile si impartirile in serie si le adauga intr-un vector de perechi.
        //Se returneaza acel vector.
        try {ValidateExpression.Validate(args, args.length);}
        catch (Exception e) {throw new RuntimeException(e.getCause().toString());}

        Vector<Pair> newExpression = new Vector<> ();
        for ( int i = 1; i < args.length; i += 2) {
            if ( this.args[i].equals("+") || this.args[i].equals("-") ) {
                newExpression.add(new Pair(NumarComplex.fromString(this.args[i-1]), Operation.getOperation(this.args[i])));
            } else if ( this.args[i].equals("*") || this.args[i].equals("/") ) {
                NumarComplex result = NumarComplex.fromString(this.args[i - 1]);
                while ( i + 1 < args.length  && (this.args[i].equals("*") || this.args[i].equals("/"))) {
                    result = (new Operation(result, NumarComplex.fromString(this.args[i + 1]), Operation.getOperation(this.args[i])).getResult());
                    i += 2;
                }
                Pair elem;
                if (i <this.args.length)
                    elem = new Pair(result, Operation.getOperation(this.args[i]));
                else elem = new Pair(result, null);
                newExpression.add(elem);
            }
        }
        return newExpression;
    }
}
