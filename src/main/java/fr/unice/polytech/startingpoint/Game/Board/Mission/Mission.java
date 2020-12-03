package fr.unice.polytech.startingpoint.Game.Board.Mission;

import fr.unice.polytech.startingpoint.Game.Bot.*;
import fr.unice.polytech.startingpoint.Game.Board.*;

public interface Mission {

    //Verifie si une mission est faite
    int checkMission(Board board, Bot bot);

}
