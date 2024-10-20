package domain.validators;

import domain.Prietenie;

public class PrietenieValidator implements Validator<Prietenie> {
    public void validate(Prietenie prietenie) throws ValidationException {
        String errors = "";
        if (prietenie.getIdFriend1() == prietenie.getIdFriend2())
            errors += "Nu poti fi prieten cu tine (desi ar trebui).";
        if (prietenie.getDate() == null)
            errors += "Data a prieteniei invalida.";
        if (!errors.equals("")) {
            throw new ValidationException(errors);
        }
    }

}
