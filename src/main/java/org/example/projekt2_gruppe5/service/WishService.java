package org.example.projekt2_gruppe5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

@Service
public class WishService {

    @Autowired
    DataSource dataSource;

    public String getImage(){
        String image = null;
        Random gen = new Random();
        int whichImg = gen.nextInt(5)+1;

        switch (whichImg){
            case 1: image = "blue-present.png"; break;
            case 2: image = "gold-present.png"; break;
            case 3: image = "green-present.png"; break;
            case 4: image = "purple-present.png"; break;
            case 5: image = "red-present.png"; break;
        }
        return image;
    }

    public boolean isWishReserved(int wishID, Connection connection){
        String selectSQL = "SELECT reservedstatus FROM wishes WHERE id = ?";

        boolean temp = false;

        try(PreparedStatement statement = connection.prepareStatement(selectSQL);){

            statement.setInt(1,wishID);
            ResultSet resultSet = statement.executeQuery();

            System.out.println(resultSet);

            resultSet.next();

            temp = resultSet.getBoolean("reservedstatus");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return temp;
    }
}
