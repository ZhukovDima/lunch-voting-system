package com.lunchvoting.repository;

import com.lunchvoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Override
    User save(User user);

    User getByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);
}
