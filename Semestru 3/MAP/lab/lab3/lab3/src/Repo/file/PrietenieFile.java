package repo.file;

import domain.Prietenie;
import domain.Tuple;
import domain.validators.Validator;

import java.time.LocalDate;
import java.util.List;

public class PrietenieFile extends AbstractFileRepository<Tuple<Long, Long>, Prietenie> {

    public PrietenieFile(String fileName, Validator<Prietenie> validator) {
        super(fileName, validator);
    }

    @Override
    public Prietenie extractEntity(List<String> attributes) {
        return new Prietenie(
            Long.parseLong(
                    attributes.get(0)),
            Long.parseLong(
                    attributes.get(1)),
            LocalDate.parse(
                    attributes.get(2))
        );
    }

    @Override
    protected String createEntityAsString(Prietenie entity) {
        return entity.getIdFriend1() + ";" +
                entity.getIdFriend2() + ";" +
                entity.getDate();
    }


}

