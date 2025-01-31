package repo;

import domain.Table;
import java.util.ArrayList;
import java.util.List;

public class RepoTables {
    private List<Table> Tables;

    public RepoTables(){
        this.Tables = new ArrayList<Table>();
        for (int i = 1; i < 21; i ++)
            this.Tables.add(new Table(i));
    }

    public List<Table> GetTables(){
        return this.Tables;
    }
}
