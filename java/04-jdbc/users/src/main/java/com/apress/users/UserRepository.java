package com.apress.users;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepository implements SimpleRepository<User, Integer> {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        Object[] params = new Object[] { id };
        User user = jdbcTemplate.queryForObject(sql, params, new int[] { Types.INTEGER }, new UserRowMapper());
        return Optional.ofNullable(user);
    }

    @Override
    public Iterable<User> findAll() {
        String sql = "SELECT * FROM users";
        return this.jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (name, email, password, gravatar_url,user_role,active) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            String[] array = user.userRole().stream().map(Enum::name).toArray(String[]::new);
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.name());
            ps.setString(2, user.email());
            ps.setString(3, user.password());
            ps.setString(4, user.gravatarUrl());
            ps.setArray(5, connection.createArrayOf("varchar", array));
            ps.setBoolean(6, user.active());
            return ps;
        }, keyHolder);

        User userCreated = user.withId((Integer)keyHolder.getKeys().get("id"));
        return userCreated;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
