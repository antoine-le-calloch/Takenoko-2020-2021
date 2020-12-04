package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.Board;
import fr.unice.polytech.startingpoint.Game.Resource;
import fr.unice.polytech.startingpoint.Type.MissionType;

/**
 * Classe qui represente un bot qui joue intelligemment en completant seulement des missions pandas
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class PandaBot extends Bot {

    public PandaBot(Resource resource, Board board) {
        super(resource, board);
        }

    @Override
    public void botPlay() {
        drawMission(MissionType.PANDA);
        randomMovePanda(possibleCoordinatesCharacter());
    }

}
