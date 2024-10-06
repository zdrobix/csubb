public class Pair {
    private final NumarComplex numar;
    private final Op operation;

    public Pair (NumarComplex numar_, Op operation_) {
        this.numar = numar_;
        this.operation = operation_;
    }

    public NumarComplex getNumar() {
        return this.numar;
    }

    public Op getOperation() {
        return this.operation;
    }
}
