package com.lws.dao;

import com.lws.model.User;

public interface IUserDao {

    User selectUser(long id);

}