import test.TestRepoMemory;
import test.TestUser;

public class Main {
    public static void main(String[] args) {

        new TestUser().testAll();
        new TestRepoMemory().testAll();
    }
}

