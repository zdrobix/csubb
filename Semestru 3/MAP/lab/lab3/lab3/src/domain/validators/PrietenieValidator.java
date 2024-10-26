package domain.validators;

import domain.Prietenie;
import domain.Tuple;

import repo.db.UserDatabaseRepository;
import repo.db.FriendshipDatabaseRepository;

import java.io.IOException;
import java.sql.SQLException;

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
    public static void validate2(Prietenie prietenie, UserDatabaseRepository repo, FriendshipDatabaseRepository repo2) throws ValidationException {
        try {
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
        } catch (SQLException ex) {
            throw new ValidationException("Cannot acces database.");
        } catch (IOException e) {
            throw new ValidationException("Cannot acces logFile.");
        }
    }

    public static void validate3 (Long id1, Long id2, UserDatabaseRepository repo) throws ValidationException {
        try {
            String errors = "";
            if (repo.findOne(id1) == null)
                errors += "Invalid id. ";
            if (repo.findOne(id2) == null)
                errors += "Invalid id. ";
            if (!errors.equals("")) {
                throw new ValidationException(errors);
            }
        } catch (SQLException ex) {
            throw new ValidationException("Cannot acces database.");
        }
    }
}
