package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.Bot;

public interface Mission {

    //Verifie si une mission est faite
    int checkMission(Board board, Bot bot);

}
