public class Operation {
    private NumarComplex nr1;
    private NumarComplex nr2;
    private Op operation;

    public Operation (NumarComplex nr1_, NumarComplex nr2_, Op operation_) {
        this.nr1 = nr1_;
        this.nr2 = nr2_;
        this.operation = operation_;
    }

    public NumarComplex getResult() {
        return switch (operation) {
            case ADD -> nr1.add(nr2);
            case SUBTRACT -> nr1.subtract(nr2);
            case MULTIPLY -> nr1.multiply(nr2);
            case DIVIDE -> nr1.divide(nr2);
        };
    }
}

enum Op {
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}