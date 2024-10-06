public class Operation {
    private final NumarComplex nr1;
    private final NumarComplex nr2;
    private final Op operation;

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

    public static Op getOperation (String string) {
        return switch (string) {
            case "+" -> Op.ADD;
            case "-" -> Op.SUBTRACT;
            case "*" -> Op.MULTIPLY;
            case "/" -> Op.DIVIDE;
            default -> null;
        };
    }
}

enum Op {
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}