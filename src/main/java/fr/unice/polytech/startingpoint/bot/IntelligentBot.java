package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.PlayerInteraction;
import fr.unice.polytech.startingpoint.game.Rules;

public class IntelligentBot extends Bot {
    StratRushPanda stratRushPanda = new StratRushPanda(this);
    StratMissionParcel stratMissionParcel = new StratMissionParcel(this);
    int NB_CHANGE_STRAT = 2;

    public IntelligentBot(PlayerInteraction playerInteraction, Rules rules) {
        super(playerInteraction, rules);
    }

    public void botPlay() {
        if (playerInteraction.getNumberMissionsDone() < NB_CHANGE_STRAT)
            stratMissionParcel.stratParcel();
        else
            stratRushPanda.stratRushPanda();
    }
}
