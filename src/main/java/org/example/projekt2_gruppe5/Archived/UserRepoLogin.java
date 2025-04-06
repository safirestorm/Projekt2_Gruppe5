package org.example.projekt2_gruppe5.Archived;

import org.example.projekt2_gruppe5.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class UserRepoLogin{

    // Spring creates connection to the database
    @Autowired
    private DataSource dataSource;

    // Method for finding a user in database based on username and password
    public User findUserByUsernameAndPassword(String username, String password){
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        // Ensures the database is closed after use
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Avoids SQL injections (replaces the ? in the query - index 1 and index 2)
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Query was executed. Found: " + resultSet);

            // Checks if resultSet is empty - could possibly also be if (!resultSet.isEmpty)
            if (resultSet.next()) {
                System.out.println("There is something in the resultset");
                // Set null as there is not a valueless constructor - did not want to fuck up the user class
                User user = new User(null, null, null, null);
                user.setUsername(resultSet.getString("username"));
                user.setFirstname(resultSet.getString("firstName"));
                user.setLastname(resultSet.getString("lastName"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }
        // Returns null if there is no match
        return null;
    }

}
