package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.Board;
import fr.unice.polytech.startingpoint.Game.Resource;

import java.util.Random;

/**
 * Classe qui represente un bot qui joue aleatoirement
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */


public class RandomBot extends Bot {
    private final Random random;

    public RandomBot(Resource resource, Board board) {
        super(resource, board);
        random = new Random();
    }

    //Action d'un bot pendant un tour
    @Override
    public void botPlay(){
        int nb = random.nextInt(5);
        if (nb == 0 && resource.getNbMissionParcel() > 0) {   // pioche mission
            drawMission();
        }
        else if (nb ==1 && resource.getCanal().size() > 0) {  // place canal
            if (possibleCoordinatesCanal().size() > 0)
                placeRandomCanal(possibleCoordinatesCanal());
        }
        else if (nb ==2 && possibleCoordinatesParcel().size() > 0){ // place parcel
            placeRandomParcel(possibleCoordinatesParcel());
        }
        else if (nb == 3 && possibleCoordinatesCharacter().size() != 0) {
            randomMovePanda(possibleCoordinatesCharacter());
        }
        else if (possibleCoordinatesCharacter().size() != 0) {
            randomMovePeasant(possibleCoordinatesCharacter());
        }
    }
}