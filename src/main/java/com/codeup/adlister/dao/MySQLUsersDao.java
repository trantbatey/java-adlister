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
        PreparedStatement stmt;
        try {
            String sql = "SELECT * FROM users WHERE username = ?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return extractUser(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user by username.", e);
        }
    }

    @Override
    public Long insert(User user) {
        try {
            PreparedStatement stmt = connection.prepareStatement(getInsertQuery(), Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
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

    @Override
    public Long getMaxId() {
        PreparedStatement stmt;
        long id;
        try {
            String sql = "SELECT max(id) AS id FROM users";
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) id = 0;
            else id = rs.getLong("id");
            return id;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user by username.", e);
        }
    }

    private User extractUser(ResultSet rs) throws SQLException {
        if (!rs.next()) return null;
        return new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password")
        );
    }
}
