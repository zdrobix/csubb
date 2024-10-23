import java.util.function.Function;

public class FunctionTest {
    public static void main(String[] args) {
        Function<String, Integer> stringToInt1 = x -> Integer.valueOf(x);
        Function<String, Integer> stringToInt2 =  Integer::valueOf;

        Function<Integer, Integer> intToSquare =  x -> x * x;

        System.out.println(stringToInt1.apply("123"));
        System.out.println(stringToInt2.apply("123"));
        System.out.println();
        System.out.println(stringToInt2.andThen(intToSquare).apply("123"));
    }
}
