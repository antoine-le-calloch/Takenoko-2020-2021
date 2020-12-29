package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.PlayerInteraction;

public class IntelligentBot extends Bot {
    RushPandaStrat rushPandaStrat = new RushPandaStrat(this);
    MissionParcelStrat stratMissionParcel = new MissionParcelStrat(this);
    int NB_CHANGE_STRAT = 2;

    public IntelligentBot(PlayerInteraction playerInteraction) {
        super(playerInteraction);
    }

    public void botPlay() {
        for (int i = playerInteraction.getStamina();i > 0; i--){
            if (playerInteraction.getNumberMissionsDone() < NB_CHANGE_STRAT)
                stratMissionParcel.stratOneTurn();
            else
                rushPandaStrat.stratOneTurn();
        }
    }
}