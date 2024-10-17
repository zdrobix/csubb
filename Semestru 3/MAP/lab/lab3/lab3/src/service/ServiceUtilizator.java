package service;

import domain.validators.ValidationException;
import repo.file.UtilizatorFile;
import domain.Utilizator;
import domain.validators.UtilizatorValidator;

public class ServiceUtilizator {
    private UtilizatorFile repo;

    public ServiceUtilizator(UtilizatorFile repo_) {
        this.repo = repo_;
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
        try {
            this.repo.delete(id);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }
}
