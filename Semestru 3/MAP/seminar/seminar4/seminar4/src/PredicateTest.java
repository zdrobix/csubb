import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class PredicateTest {
    public static<E> void print(List<E> lista, Predicate<E> p) {
        lista.forEach(x -> {
            if(p.test(x))
                System.out.println(x);
        } );
    }
    public static void main(String[] args) {
        Cerc cerc = new Cerc(10);
        Cerc cerc1 = new Cerc(21);
        Cerc cerc2 = new Cerc(30);
        Cerc cerc3 = new Cerc(41);

        List<Cerc> lista = Arrays.asList(cerc, cerc1, cerc2, cerc3);

        Predicate<Cerc> predicateCerc = x -> x.getRadius() < 30;
        Predicate<Cerc> predicateCerc2 = predicateCerc.negate();
        Predicate<Cerc> predicateCerc3 = predicateCerc.and(x -> x.getRadius() % 2 == 1);
        print(lista, predicateCerc);
        print(lista, predicateCerc2);
        print(lista, predicateCerc3);


    }
}
