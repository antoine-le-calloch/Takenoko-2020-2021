package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.Bot;
import fr.unice.polytech.startingpoint.Type.*;

/**
 * Classe representant une mission panda
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class PandaMission implements Mission {
    private final int points;
    private final ColorType colorType;

    public PandaMission(int points, ColorType colorType){
        this.colorType = colorType;
        this.points = points;
    }

    public int checkMission(Board board, Bot bot) {
        return checkMissionPanda(bot);
    }

    public int checkMissionPanda(Bot bot){
        if (bot.getInventory().subBamboo(colorType)){
            return points;
        }
        return 0;
    }
}