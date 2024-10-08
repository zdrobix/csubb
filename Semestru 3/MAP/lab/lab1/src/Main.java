public class Main {
    public static void main(String[] args) {
        TestApp test = new TestApp();
        test.testAll();
        System.out.println(new ExpressionCalc(args).getExpressionResult().toString());
    }
}


