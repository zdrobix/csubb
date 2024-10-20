package service;

import domain.Prietenie;
import domain.Tuple;
import domain.validators.PrietenieValidator;
import domain.validators.ValidationException;
import repo.file.UtilizatorFile;
import repo.file.PrietenieFile;
import domain.Utilizator;

import java.time.LocalDate;

public class Service {
    private UtilizatorFile repo;
    private PrietenieFile repoPrieteni;

    public Service(UtilizatorFile repo_, PrietenieFile repoPrieteni_) {
        this.repo = repo_;
        this.repoPrieteni = repoPrieteni_;
    }

    public void addUtilizator (String firstName, String lastName, long id) {
        try {
            var user = new Utilizator(firstName, lastName);
            user.setId(id);
            this.repo.save(user);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public void deleteUtilizator (long id) {
        if (this.repo.findOne(id) == null) {
            throw new ValidationException("User not found");
        }
        try {
            for (var prietenie : this.repoPrieteni.findAll())
            {
                if (prietenie.getIdFriend1() == id || prietenie.getIdFriend2() == id)
                    this.repoPrieteni.delete(prietenie.getId());
            }
            this.repo.delete(id);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public void addPrietenie (long id1, long id2)
    {
        try {
            var friends = new Prietenie(id1, id2, LocalDate.now());
            friends.setId(
                    new Tuple<Long, Long>(
                            id1, id2));
            PrietenieValidator.validate2(friends, this.repoPrieteni, this.repo);
            this.repoPrieteni.save(friends);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public void deletePrietenie (long id1, long id2) {
        try {
            var friends = this.repoPrieteni.findOne(new Tuple<Long, Long>(
                    id1, id2));
            PrietenieValidator.validate3(id1, id2, this.repo);
            this.repoPrieteni.delete(new Tuple<Long, Long>(
                    id1, id2));
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }
}
