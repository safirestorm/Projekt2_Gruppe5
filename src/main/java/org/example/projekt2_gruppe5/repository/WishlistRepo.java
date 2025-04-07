package org.example.projekt2_gruppe5.repository;

import org.example.projekt2_gruppe5.model.Wish;
import org.example.projekt2_gruppe5.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class WishlistRepo {

    @Autowired
    private DataSource dataSource; //laver fejl hvis appilication.properties er fejl

    public ArrayList<Wishlist> getAllWishlist() {
        ArrayList<Wishlist> wishListList = new ArrayList<>();
        String sql = "SELECT * FROM wishlists WHERE userID = ?"; // string sql statement

        try (Connection connection = dataSource.getConnection(); // man laver en connecttion
             PreparedStatement statement = connection.prepareStatement(sql); // PreparedSatement sørger for at formateringen er korrekt til sql
             ResultSet resultSet = statement.executeQuery()) { // ResultSet er måden java gemmer dataen der kommer tilbage fra databsen

            while (resultSet.next()) {
                Wishlist wishlist = new Wishlist();
                wishlist.setId(resultSet.getInt("id"));
                wishlist.setName(resultSet.getString("name"));
                wishlist.setExpirationDate(resultSet.getDate("date").toLocalDate());
                wishlist.setDescription(resultSet.getString("description"));
                System.out.println("Found a wishlist! /n" + wishlist);
                wishListList.add(wishlist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wishListList;
    }
    public void saveWishlist(Wishlist wishlist) {
        String sql = "INSERT INTO wishlists (id, userID, name, date, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) { //

            statement.setInt(1, wishlist.getId());
            statement.setString(2, wishlist.getUserId());
            statement.setString(3, wishlist.getName());
            statement.setDate(4, Date.valueOf(wishlist.getExpirationDate()));
            statement.setString(5, wishlist.getDescription());

            statement.executeUpdate(); // eksekverer dit statemtn
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteWishlist(int id) {
        String sql = "DELETE FROM wishlists WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Wishlist getWishlistByUsername(String username) {
        Wishlist wishlist = null;
        String sql = "SELECT * FROM wishlist WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(2, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    wishlist = new Wishlist();
                    wishlist.setId(resultSet.getInt("id"));
                    wishlist.setUserId(resultSet.getString("userID"));
                    wishlist.setName(resultSet.getString("name"));
                    wishlist.setExpirationDate(LocalDate.parse(resultSet.getString("type")));
                    wishlist.setDescription(resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wishlist;
    }
    public void updateWishlist(Wishlist wishlist) {
        String sql = "UPDATE wishlists SET id = ?, userID = ?, name = ?, date = ?, description = ?, WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, wishlist.getId());
            statement.setString(2, wishlist.getUserId());
            statement.setString(3, wishlist.getName());
            statement.setString(4, String.valueOf(wishlist.getExpirationDate()));
            statement.setString(5, wishlist.getDescription());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

