package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.WeatherType;

import java.util.Random;

public class WeatherDice {


    private Random random;
    private final int FACES= WeatherType.values().length;

    WeatherDice(Random random){
            this.random=random;
    }

    WeatherType roll(){
        int result=random.nextInt(FACES);
        if(result<0 || result>FACES)
            throw new RuntimeException("Dice return an incompatible value");
        return WeatherType.values()[result];
    }
}


