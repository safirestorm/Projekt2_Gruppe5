package org.example.projekt2_gruppe5.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class WishService {

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
}
