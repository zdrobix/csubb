import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamsTest {
    public static <E> List<E> filterGeneric1(List<E> lista, Predicate<E> p, Comparator<E> c) {
        return lista.stream()
                .filter(p)
                .sorted(c)
                .collect(Collectors.toList());
    }
    public static <E> List<E> filterGeneric2(List<E> lista, Predicate<E> p) {
        return lista.stream()
                .filter(p)
                .collect(Collectors.toList());
    }
    public static void main(String args[]) {
        List<Integer> lista = Arrays.asList(2, 4, 31, 10, 8, 0, 53, 5,3 ,1);
        Predicate<Integer> predicat = x -> x % 2 == 0;
        System.out.println(filterGeneric2(lista, predicat));
        Comparator<Integer> comparator = Integer::compare;

        System.out.println(filterGeneric1(lista, predicat, comparator));

    }
}
