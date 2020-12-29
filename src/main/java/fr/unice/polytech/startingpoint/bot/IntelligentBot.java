package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.PlayerInteraction;
import fr.unice.polytech.startingpoint.game.Rules;
import fr.unice.polytech.startingpoint.type.ActionType;

import java.util.ArrayList;
import java.util.List;

public class IntelligentBot extends Bot {
    RushPandaStrat rushPandaStrat = new RushPandaStrat(this);
    MissionParcelStrat stratMissionParcel = new MissionParcelStrat(this);
    int NB_CHANGE_STRAT = 2;

    public IntelligentBot(PlayerInteraction playerInteraction) {
        super(playerInteraction);
    }

    public void botPlay() {
        while(playerInteraction.getStamina()!=0){
            if (playerInteraction.getNumberMissionsDone() < NB_CHANGE_STRAT)
                stratMissionParcel.stratOneTurn();
            else
                rushPandaStrat.stratOneTurn();
        }
    }
}