package com.imooc.reader.service;

import com.imooc.reader.entity.User;

public interface UserService {
    /**
     * Check user login
     * @param username
     * @param password
     * @return
     */
    User checkLogin(String username, String password);
}
