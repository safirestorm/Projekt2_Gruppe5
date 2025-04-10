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
        ArrayList<Integer> wishlistIds = new ArrayList<>();

        String getWishlistIDs = "SELECT id FROM wishlists WHERE userID = ?";
        String deleteWishes = "DELETE FROM wishes WHERE wishlistID = ?";
        String deleteWishlists = "DELETE FROM wishlists WHERE userID = ?";
        String deleteUser = "DELETE FROM users WHERE username = ?";


        try (Connection connection = dataSource.getConnection()) {

            // Starts transaction
            connection.setAutoCommit(false);

            // Get wishlist IDs and add them to an arraylist

            // Make prepared statement - avoid injections
            try (PreparedStatement psGetWishlistIDs = connection.prepareStatement(getWishlistIDs)) {
                psGetWishlistIDs.setString(1, user.getUsername());

                // Add ids to a resultset - then add them to an arraylist
                try (ResultSet resultSetGetWishlistIDs = psGetWishlistIDs.executeQuery()) {
                    while (resultSetGetWishlistIDs.next()) {
                        wishlistIds.add(resultSetGetWishlistIDs.getInt("id"));
                    }
                }
            }

            // Delete wishes based on wishlist IDs in the arraylist

            try (PreparedStatement psDeleteWishes = connection.prepareStatement(deleteWishes)) {

                // For all ids in the arraylist, set the wishlist ID and delete wishes that match
                for (Integer id : wishlistIds) {
                    psDeleteWishes.setInt(1, id);
                    psDeleteWishes.executeUpdate();
                }
            }

            // Delete wishlists

            try (PreparedStatement psDeleteWishlist = connection.prepareStatement(deleteWishlists)) {
                psDeleteWishlist.setString(1, user.getUsername());
                psDeleteWishlist.executeUpdate();
            }

            // Delete user

            try (PreparedStatement psDeleteUser = connection.prepareStatement(deleteUser)) {
                psDeleteUser.setString(1, user.getUsername());
                psDeleteUser.executeUpdate();
            }

            // End transaction
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();

            // If the transaction fails, it will try to rollback so no queries are excecuted
            try (
                    Connection connection = dataSource.getConnection()) {
                connection.rollback();
            } catch (SQLException rollback) {
                rollback.printStackTrace();
            }
        }
    }
}
