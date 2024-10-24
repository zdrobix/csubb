import controller.Ui;
import domain.validators.PrietenieValidator;
import domain.validators.UtilizatorValidator;
import repo.file.PrietenieFile;
import repo.file.UtilizatorFile;
import service.Service;

public class Main {
    public static void main(String[] args) {
        new Ui(
                new Service(
                        new UtilizatorFile(
                                "Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\input\\users.txt",
                                new UtilizatorValidator()
                        ),
                        new PrietenieFile(
                                "Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\input\\friendship.txt",
                                new PrietenieValidator()
                        ))).Run();
    }
}

