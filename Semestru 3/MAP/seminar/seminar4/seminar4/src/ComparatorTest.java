import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ComparatorTest {
    public static void main(String[] args) {
        Comparator<Integer> comparator1 = (x, y) -> Integer.compare(y, x); //descrescator
        Comparator<Integer> comparator2 = Integer::compare;                //crescator

        List<Integer> list = Arrays.asList(1, 19, 29, 34, 541, 21, 54, 2, 53, 12, 12);

        list.sort(comparator1);//desc
        System.out.println(list);

        list.sort(comparator2);//cresc
        System.out.println(list);

        list.sort(comparator1.reversed());//cresc
        System.out.println(list);

        list.sort(comparator2.reversed());//desc
        System.out.println(list);

    }
}
