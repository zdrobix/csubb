package org.example.repository.interf;

import org.example.domain.LoginInfo;

public interface ILoginRepository extends IRepository {
    LoginInfo GetByUsername (String username);
}
