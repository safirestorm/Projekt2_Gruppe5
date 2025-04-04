package org.example.projekt2_gruppe5.service;

import org.example.projekt2_gruppe5.exceptions.UserNotCreatedException;
import org.example.projekt2_gruppe5.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class UserService {

    @Autowired
    DataSource dataSource;

    public boolean isUserNameAvailable(String username){
        boolean isNotAvailable = false;
        String sql = "SELECT username FROM users;";

        try(
                //Establish connection to the database and execute the query, selecting all usernames
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                ){
            //Loop through the resultset
            while(resultSet.next()){
                //Check if the username of the current row matches the username of the new user
                if (username.equals(resultSet.getString("username"))){
                    //If it matches, return true. Meaning "THE USERNAME IS ALREADY TAKEN"
                    return true;
                }
            }

            //When the loop is over, that means the username is available, so we reutrn false, meaning "THIS USERNAME IS NOT TAKE"
            return false;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return isNotAvailable;
    }

    public void saveNewUser(User user) throws UserNotCreatedException{
        String sql = "INSERT INTO users (username, password, firstname, lastname) VALUES (?,?,?,?)";

        //Establish SQL connection
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            //Set placeholder values in statement to correct values
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            System.out.println(user.getFirstname());
            System.out.println(user.getLastname());
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
}

