package domain.validators;

import domain.Prietenie;
import domain.Tuple;
import repo.file.PrietenieFile;
import repo.file.UtilizatorFile;

public class PrietenieValidator implements Validator<Prietenie> {
    public void validate(Prietenie prietenie) throws ValidationException {
        String errors = "";
        if (prietenie.getIdFriend1() == prietenie.getIdFriend2())
            errors += "You can't friend yourself. ";
        if (prietenie.getDate() == null)
            errors += "The starting date of the friendship is invalid. ";
        if (!errors.equals("")) {
            throw new ValidationException(errors);
        }
    }
    public static void validate2(Prietenie prietenie, PrietenieFile repo2, UtilizatorFile repo) throws ValidationException {
        String errors = "";
        if (repo.findOne(prietenie.getIdFriend1()) == null)
            errors += "Invalid id. ";
        if (repo.findOne(prietenie.getIdFriend2()) == null)
            errors += "Invalid id. ";
        if (repo2.findOne(new Tuple<Long, Long>(prietenie.getIdFriend2(), prietenie.getIdFriend1())) != null)
            errors += "Already friends. ";
        if (repo2.findOne(prietenie.getId()) != null)
            errors += "Already friends. ";
        if (!errors.equals("")) {
            throw new ValidationException(errors);
        }
    }

    public static void validate3 (long id1, long id2, UtilizatorFile repo) throws ValidationException {
        String errors = "";
        if (repo.findOne(id1) == null)
            errors += "Invalid id. ";
        if (repo.findOne(id2) == null)
            errors += "Invalid id. ";
        if (!errors.equals("")) {
            throw new ValidationException(errors);
        }
    }
}
