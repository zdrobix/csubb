package service;

import domain.validators.ValidationException;
import repo.file.UtilizatorFile;
import repo.file.PrietenieFile;
import domain.Utilizator;

public class ServiceUtilizator {
    private UtilizatorFile repo;
    private PrietenieFile repoPrieteni;

    public ServiceUtilizator(UtilizatorFile repo_, PrietenieFile repoPrieteni_) {
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

            this.repo.delete(id);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }
}
