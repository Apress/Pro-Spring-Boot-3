package com.apress.users;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        Array array = rs.getArray("user_role");
        String[] rolesArray = Arrays.copyOf((Object[])array.getArray(), ((Object[])array.getArray()).length, String[].class);
        List<UserRole> roles = Arrays.stream(rolesArray).map(UserRole::valueOf).collect(Collectors.toList());

        User newUser = User.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .userRole(roles)
                .build();

        return newUser;
    }
}
