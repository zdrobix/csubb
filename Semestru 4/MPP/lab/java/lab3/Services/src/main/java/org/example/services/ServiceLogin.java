package org.example.services;

import org.example.domain.LoginInfo;
import org.example.repository.interf.ILoginRepository;

public class ServiceLogin implements IServiceLogin{
    private ILoginRepository Repo;

    public ServiceLogin(ILoginRepository repo) {
        this.Repo = repo;
    }

    public LoginInfo GetByUsername (String username) {
        return this.Repo.GetByUsername(username);
    }
}
