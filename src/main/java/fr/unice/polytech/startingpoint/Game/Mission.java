package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.Bot;

/**
 * Interface representant les caractéristiques communes des missions
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public interface Mission {

    //Verifie si une mission est faite
    int checkMission(Board board, Bot bot);

}
