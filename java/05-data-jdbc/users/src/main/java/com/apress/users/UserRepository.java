package com.apress.users;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long>{
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);

    // Example of a custom query
    @Query("SELECT GRAVATAR_URL FROM USERS WHERE EMAIL = :email")
    String getGravatarByEmail(@Param("email") String email);

}
