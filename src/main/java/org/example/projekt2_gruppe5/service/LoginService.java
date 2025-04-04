package org.example.projekt2_gruppe5.service;

import org.example.projekt2_gruppe5.model.User;
import org.example.projekt2_gruppe5.repository.UserRepoLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// This whole class should probably be integrated into the UserService class somehow at some point

@Service
public class LoginService {

    // Allows use of UserRepoLogin
    @Autowired
    private UserRepoLogin userRepoLogin;

    // Method for setting a current user
    private User currentUser;
    public boolean login(String username, String password) {
        System.out.println("Arrived at login service");
        User user = userRepoLogin.findUserByUsernameAndPassword(username, password);
        if (user!=null) {
            System.out.println("user was found");
            currentUser = user;
            return true;
        }
        System.out.println("user was null");
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

}
