import controller.Ui;
import domain.validators.UtilizatorValidator;
import repo.file.UtilizatorFile;
import service.ServiceUtilizator;
import test.TestRepoFile;
import test.TestRepoMemory;
import test.TestUser;

public class Main {
    public static void main(String[] args) {
        new Ui(
                new ServiceUtilizator(
                        new UtilizatorFile(
                                "Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\input\\input.txt",
                                new UtilizatorValidator()
                        ))).Run();
    }
}

