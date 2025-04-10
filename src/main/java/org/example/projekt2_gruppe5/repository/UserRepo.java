package org.example.projekt2_gruppe5.repository;

import jakarta.servlet.http.HttpSession;
import org.example.projekt2_gruppe5.exceptions.UserNotCreatedException;
import org.example.projekt2_gruppe5.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepo {

    //User currentUser;

    HashMap<String, User> loggedInUsers = new HashMap<>();

@Autowired
DataSource dataSource;
    @Autowired
    private HttpSession httpSession;

    public void saveNewUser(User user) throws UserNotCreatedException {
        String sql = "INSERT INTO users (username, password, firstname, lastname) VALUES (?,?,?,?)";

        //Establish SQL connection
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        )
        {
            //Set placeholder values in statement to correct values
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstname());
            statement.setString(4, user.getLastname());

            //Execute the update
            statement.executeUpdate();

        }catch (SQLException e){
            System.out.println("Failed to create user, SQL error encountered.");
            e.printStackTrace();

            //If for some other reason the SQL fails, throw an exception to be handled in the controller, so the error can be displayed (Fx in case of too long names)
            throw new UserNotCreatedException();
        }
    }

    public User getCurrentUser(){
        User user = loggedInUsers.get(httpSession.getId());

        return user;
    }

    public boolean login(String username, String password) {
        System.out.println("Arrived at login service");
        User user = findUser(username, password);

        if (user!=null) {
            System.out.println("user was found with username " + user.getUsername());
            loggedInUsers.put(httpSession.getId(), user);
            return true;
        }

        System.out.println("user was null");
        return false;
    }

    // Method for finding a user in database based on username and password
    public User findUser(String username, String password){
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

    public void deleteUser(User user) {

        // Get wishlist ID of all the users wishlists
        ArrayList<Integer> wishlistIDs = new ArrayList<>();

        String sqlGetWishlistIDs = "SELECT id FROM wishlists WHERE userID = ?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statementGetWLIDs = connection.prepareStatement(sqlGetWishlistIDs);
                )
        {
            statementGetWLIDs.setString(1,user.getUsername());
            ResultSet resultSetGetWLIDs = statementGetWLIDs.executeQuery();

            // Add the IDs to the arraylist
            while(resultSetGetWLIDs.next()) {
                wishlistIDs.add(resultSetGetWLIDs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Delete the users wishes
        for (Integer wishlistID : wishlistIDs) {
        String sqlDeleteWishes = "DELETE FROM wishes WHERE wishlistID = ?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statementDeleteWishes = connection.prepareStatement(sqlDeleteWishes);
                )
        {
            statementDeleteWishes.setInt(1,wishlistID);

            statementDeleteWishes.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        }

        // Delete all the users wishlists
        String sqlDeleteWishlists = "DELETE FROM wishlists WHERE userID = ?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statementDeleteWishlists = connection.prepareStatement(sqlDeleteWishlists);
                )
        {
            statementDeleteWishlists.setString(1, user.getUsername());

            statementDeleteWishlists.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Delete user :(
        String sqlDeleteUser = "DELETE FROM users WHERE username = ?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statementDeleteUser = connection.prepareStatement(sqlDeleteUser);
                )
        {
            statementDeleteUser.setString(1, user.getUsername());

            statementDeleteUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
