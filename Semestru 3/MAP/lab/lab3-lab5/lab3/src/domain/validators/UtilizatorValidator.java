package domain.validators;

import domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String errors = "";
        if (entity.getFirstName().isEmpty())
            errors += "Prenumele nu poate fi gol.";
        if (entity.getLastName().isEmpty())
            errors += "Numele nu poate fi gol.";
        if (!errors.equals("")) {
            throw new ValidationException(errors);
        }
    }
}
