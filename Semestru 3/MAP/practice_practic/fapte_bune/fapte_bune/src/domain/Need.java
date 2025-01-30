package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.AEADBadTagException;

public class Need extends Entity{
    private String Title;
    private String Description;
    private LocalDateTime Deadline;
    private long NeedyPersonID;
    private long SavingPersonID;
    private String Status;

    public Need(long ID, String title, String description, LocalDateTime deadline, long needyPersonID, long savingPersonID, String status) {
        this.Title = title;
        this.Description = description;
        this.Deadline = deadline;
        this.NeedyPersonID = needyPersonID;
        this.SavingPersonID = savingPersonID;
        this.Status = status;
        super.SetID(ID);
    }

    public String GetTitle() {
        return Title;
    }

    public String GetDescription() {
        return Description;
    }

    public LocalDateTime GetDeadline() {
        return Deadline;
    }

    public long GetNeedyPersonID() {
        return NeedyPersonID;
    }

    public long GetSavingPersonID() {
        return SavingPersonID;
    }

    public String GetStatus() {
        return Status;
    }

    public List<String> GetParams() {
        var list = new ArrayList<String>();
        list.add("" + super.GetID());
        list.add(this.GetTitle());
        list.add(this.GetDescription());
        list.add("" + this.GetDeadline());
        list.add("" + this.GetNeedyPersonID());
        list.add("" + this.GetSavingPersonID());
        list.add(this.GetStatus());
        return list;
    }
}
