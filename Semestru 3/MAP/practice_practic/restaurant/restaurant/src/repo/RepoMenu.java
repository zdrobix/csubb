package repo;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.MenuItem;

public class RepoMenu {
    private List<MenuItem> Menu;

    public RepoMenu(){
        Menu = new ArrayList<>();
        this.LoadFromFile();
    }

    private void LoadFromFile() {

        try {
            var fileWriter = new Scanner(new File("Q:\\info\\csubb\\Semestru 3\\MAP\\practice_practic\\restaurant\\restaurant\\src\\input\\menu.txt"));
            while (fileWriter.hasNextLine()) {
                var line = fileWriter.nextLine().strip();
                var parts = line.split(",");
                this.Menu.add(
                    new MenuItem(Integer.parseInt(parts[0]), parts[1], parts[2], Float.parseFloat(parts[3]), parts[4])
                );
            }
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MenuItem> GetMenu(){
        return Menu;
    }
}
