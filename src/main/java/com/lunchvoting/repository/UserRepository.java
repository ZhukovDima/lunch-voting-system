package com.lunchvoting.repository;

import com.lunchvoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Override
    User save(User user);

    User getByEmail(String email);
}
