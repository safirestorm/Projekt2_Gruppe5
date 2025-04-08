package org.example.projekt2_gruppe5.repository;

import org.example.projekt2_gruppe5.model.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class WishRepo {

    @Autowired
    DataSource dataSource;

    // Få alle ønsker på en ønskeliste ud fra ønskelisteId
    public ArrayList<Wish> getAllWishesOnWishlist(int wishlistId) {
    ArrayList<Wish> wishList = new ArrayList<>();

    // SQL forespørgsel
    String sql = "SELECT * FROM wishes WHERE wishlistID = ?";

    try(Connection connection = dataSource.getConnection(); // Prøver at oprette forbindelse til databasen
        PreparedStatement statement = connection.prepareStatement(sql)) { // Hvis forbindelsen bliver oprettet, sender vores SQL forespørsel

        statement.setInt(1, wishlistId); // indsætter variabel ind i vores SQL forespørgsel

        try (ResultSet resultSet = statement.executeQuery()) {  // returnerer resultatet af SQL forespøgslen

            // Opretter objekter af resultatet og gemmer i liste
            while (resultSet.next()) {
                Wish wish = new Wish();
                wish.setWishId(resultSet.getInt("id"));
                wish.setName(resultSet.getString("name"));
                wish.setPrice(resultSet.getInt("price"));
                wish.setLink(resultSet.getString("link"));
                wish.setDescription(resultSet.getString("description"));
                wish.setImage(resultSet.getString("image"));
                wish.setReserved(resultSet.getBoolean("reservedstatus"));
                wishList.add(wish);
            }
        }
    } catch(SQLException e) {
        e.printStackTrace();
    }
    return wishList;
    }

    // Gemme ønske på ønskeliste
    public void saveWish(Wish wish, int wishlistId) {
        // SQL forespørgsel
        String sql = "INSERT INTO wishes (wishlistID, name, price, link, description, image) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);){

            statement.setInt(1, wishlistId);
            statement.setString(2, wish.getName());
            statement.setInt(3, wish.getPrice());
            statement.setString(4, wish.getLink());
            statement.setString(5, wish.getDescription());
            statement.setString(6, wish.getImage());

            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Slette et ønske ud fra id
    public void deleteWish(int id) {
        String sql = "DELETE FROM wishes WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
