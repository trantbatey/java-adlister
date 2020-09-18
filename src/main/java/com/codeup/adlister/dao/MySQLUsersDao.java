package com.codeup.adlister.dao;

import com.codeup.adlister.models.User;
import com.mysql.cj.jdbc.Driver;

import java.sql.*;

public class MySQLUsersDao implements Users {
    private Connection connection;

    public MySQLUsersDao(Config config) {
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                config.getUrl(),
                config.getUsername(),
                config.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!", e);
        }
    }


    @Override
    public User findByUsername(String username) {
        /* using old style statement */
        String query = String.format("SELECT * FROM users WHERE username = '%s' LIMIT 1;", username);
        try {
            Statement stmt = connection.createStatement();
            return extractUser(stmt.executeQuery(query));
        } catch (SQLException e) {
            throw new RuntimeException("Error finding a user by username", e);
        }
        /* use a prepared statement */
//        String query = "SELECT * FROM users WHERE username = ? LIMIT 1";
//        try {
//            PreparedStatement stmt = connection.prepareStatement(query);
//            stmt.setString(1, username);
//            return extractUser(stmt.executeQuery());
//        } catch (SQLException e) {
//            throw new RuntimeException("Error finding a user by username", e);
//        }
    }

    @Override
    public Long insert(User user) {
        try {
            String sql = String.format("INSERT INTO Users(id, username, email, password) VALUES ('%d', '%s', '%s', '%s');",
                    user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            PreparedStatement stmt = connection.prepareStatement(getInsertQuery(), Statement.RETURN_GENERATED_KEYS);
//            stmt.setLong(1, user.getId());
//            stmt.setString(2, user.getUsername());
//            stmt.setString(3, user.getEmail());
//            stmt.setString(4, user.getPassword());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating a new user.", e);
        }
    }

    private String getInsertQuery() {
        return "INSERT INTO Users(id, username, email, password) VALUES (?, ?, ?, ?);";
    }

    private User extractUser(ResultSet rs) throws SQLException {
        if (! rs.next()) {
            return null;
        }
        return new User(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password")
        );
    }

}
