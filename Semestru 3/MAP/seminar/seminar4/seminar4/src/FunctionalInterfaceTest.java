import java.util.Arrays;
import java.util.List;

public class FunctionalInterfaceTest {
    public static <E> void printArie(List<E> l, Arie<E> f) {
        l.forEach(x ->
                System.out.println(f.calculeaza(x))
        );
    }
    public static void main(String[] args) {
        Arie<Cerc> arieCerc = x -> Math.PI * x.getRadius() * x.getRadius();
        Cerc cerc = new Cerc(10);
        Cerc cerc1 = new Cerc(20);
        Cerc cerc2 = new Cerc(30);
        Cerc cerc3 = new Cerc(40);
        List<Cerc> listCerc = Arrays.asList(cerc, cerc1, cerc2, cerc3);
        printArie(listCerc, arieCerc);

        Arie<Patrat> ariePatrat = x -> x.getLatura() * x.getLatura();
        Patrat patrat = new Patrat(10);
        Patrat patrat1 = new Patrat(20);
        Patrat patrat2 = new Patrat(30);
        Patrat patrat3 = new Patrat(40);
        List<Patrat> listPatrat = Arrays.asList(patrat, patrat1, patrat2, patrat3);
        printArie(listPatrat, ariePatrat);
    }
}
