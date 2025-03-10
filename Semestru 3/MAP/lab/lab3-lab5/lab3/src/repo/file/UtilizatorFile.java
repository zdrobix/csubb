package repo.file;

import domain.Utilizator;
import domain.validators.Validator;

import java.util.List;

public class UtilizatorFile extends AbstractFileRepository<Long, Utilizator> {

    public UtilizatorFile(String fileName, Validator<Utilizator> validator) {
        super(fileName, validator);
    }

    @Override
    public Utilizator extractEntity(List<String> attributes) {
        Utilizator user = new Utilizator(
                attributes.get(1),
                attributes.get(2));
        user.setId(
                Long.parseLong(
                        attributes.get(0)));

        return user;
    }

    @Override
    protected String createEntityAsString(Utilizator entity) {
        return entity.getId() + ";"
                + entity.getFirstName()+ ";"
                + entity.getLastName();
    }
}
