package org.example.projekt2_gruppe5.repository;

import org.example.projekt2_gruppe5.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class WishlistRepo {

    @Autowired
    private DataSource dataSource; //laver fejl hvis appilication.properties er fejl

    public ArrayList<Wishlist> getAllWishlist() {
        ArrayList<Wishlist> wishListList = new ArrayList<>();
        String sql = "SELECT * FROM wishlists"; // string sql statement

        try (Connection connection = dataSource.getConnection(); // man laver en connecttion
             PreparedStatement statement = connection.prepareStatement(sql); // PreparedSatement sørger for at formateringen er korrekt til sql
             ResultSet resultSet = statement.executeQuery()) { // ResultSet er måden java gemmer dataen der kommer tilbage fra databsen

            while (resultSet.next()) {
                Wishlist wishlist = new Wishlist();
                wishlist.setId(resultSet.getInt("id"));
                wishlist.setName(resultSet.getString("name"));
                wishlist.setExpirationDate(resultSet.getDate("date").toLocalDate());
                wishlist.setDescription(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wishListList;
    }
}
