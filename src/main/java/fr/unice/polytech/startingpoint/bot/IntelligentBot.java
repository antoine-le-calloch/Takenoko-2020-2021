package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.type.WeatherType;

public class IntelligentBot extends Bot {
    RushPandaStrat rushPandaStrat = new RushPandaStrat(this);
    MissionParcelStrat stratMissionParcel = new MissionParcelStrat(this);
    int NB_CHANGE_STRAT = 2;

    public IntelligentBot(GameInteraction gameInteraction) {
        super(gameInteraction);
    }

    @Override
    public void botPlay(WeatherType weatherType) {

        for (int i = gameInteraction.getStamina(); i > 0; i--){
            if (gameInteraction.getNumberMissionsDone() < NB_CHANGE_STRAT)
                stratMissionParcel.stratOneTurn(weatherType);
            else
                rushPandaStrat.stratOneTurn(weatherType);
        }
    }
}