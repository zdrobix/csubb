package org.example.services;

import org.example.domain.LoginInfo;

public interface IServiceLogin {
    LoginInfo GetByUsername (String username);
}
