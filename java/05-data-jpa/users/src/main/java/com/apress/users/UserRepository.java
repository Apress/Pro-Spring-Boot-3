package com.apress.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long>{
    Optional<User> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
