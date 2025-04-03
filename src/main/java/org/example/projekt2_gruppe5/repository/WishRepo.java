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

    public ArrayList<Wish> getAllWishes() {
    ArrayList<Wish> wishList = new ArrayList<>();
    String sql = "SELECT * FROM wishes";

    try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()){
            Wish wish = new Wish();
            wish.setWishId(resultSet.getInt("id"));
            wish.setName(resultSet.getString("name"));
            wish.setPrice(resultSet.getInt("price"));
            wish.setLink(resultSet.getString("link"));
            wish.setImage(resultSet.getString("image"));
            wishList.add(wish);
        }

    } catch(SQLException e) {
        e.printStackTrace();
    }
    return wishList;
    }
}
