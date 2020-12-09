package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.Board;
import fr.unice.polytech.startingpoint.Game.Coordinate;
import fr.unice.polytech.startingpoint.Game.Resource;
import fr.unice.polytech.startingpoint.Type.MissionType;

import java.util.List;

/**
 * Classe qui represente un bot qui joue intelligemment en completant seulement des missions paysans
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class PeasantBot extends Bot {

    public PeasantBot(Resource resource, Board board) {
        super(resource, board);
    }

    @Override
    public void botPlay() {
        if (inventory.getMissions().size() < 5 && resource.getDeckPeasantMission().size() > 0)
            drawMission(MissionType.PEASANT);
        else if (strategyMovePeasant(possibleCoordinatesPeasant()) != null)
            movePeasant(strategyMovePeasant(possibleCoordinatesPeasant()));
    }

    public Coordinate strategyMovePeasant(List<Coordinate> listCoord) {
        for (Coordinate coordinate : listCoord) {
            if (board.getPlacedParcels().get(coordinate).getNbBamboo() > 1) {
                return coordinate;
            }
        }
        return null;
    }
}