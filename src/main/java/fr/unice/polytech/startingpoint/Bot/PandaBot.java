package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.Board;
import fr.unice.polytech.startingpoint.Game.Coordinate;
import fr.unice.polytech.startingpoint.Game.Resource;
import fr.unice.polytech.startingpoint.Type.MissionType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        randomMovePanda(strategyMovePanda(possibleCoordinatesCharacter()));
    }

    //Bouge le panda à un endroit aléatoire de la liste passée en paramètre - ACTION 4
    public List<Coordinate> strategyMovePanda(List<Coordinate> listCoord) {
        Set<Coordinate> list = new HashSet<>();
        for (Coordinate coordinate : listCoord){
            if (board.getPlacedParcels().get(coordinate).getNbBamboo() > 0){
                list.add(coordinate);
            }
        }
        return new ArrayList<>(list);
    }
}
