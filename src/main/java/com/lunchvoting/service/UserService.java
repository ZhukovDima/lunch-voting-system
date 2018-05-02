package com.lunchvoting.service;

import com.lunchvoting.model.User;

public interface UserService {
    User getByEmail(String email);
}
