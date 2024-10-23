import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Supplier;

public class SupplierTest {
    public static void main(String[] args) {
        Supplier<List> supplier1 = () -> new ArrayList<String>();
        Supplier<List> supplier2 = ArrayList::new;
        Supplier<LocalDate> supplier3 = LocalDate::now;
        Supplier<LocalDate> supplier4 = () -> LocalDate.now();

        System.out.println(supplier1.get());
        System.out.println(supplier2.get());
        System.out.println(supplier3.get());
        System.out.println(supplier4.get());
    }
}
