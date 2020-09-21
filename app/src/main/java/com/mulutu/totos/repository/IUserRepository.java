package com.mulutu.totos.repository;

import com.mulutu.totos.models.User;

public interface IUserRepository {

    void doesUserEmailExist(String email);

    void addNewRegisteredUser(User user);
}
