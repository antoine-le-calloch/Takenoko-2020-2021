package fr.unice.polytech.startingpoint;

import java.util.Random;

class RandomBot extends Bot {
    private final Random random;

    RandomBot(Resource resource, Board board) {
        super(resource, board);
        random = new Random();
    }

    //Action d'un bot pendant un tour
    @Override
    void botPlay(){
        int nb = random.nextInt(5);
        if (nb == 0 && resource.getNbMissionParcel() > 0) {   // pioche mission
            drawMission();
        }
        else if (nb ==1 && resource.getCanal().size() > 0) {  // place canal
            if (possibleCoordinatesCanal().size() > 0)
                placeRandomCanal(possibleCoordinatesCanal());
        }
        else if (nb ==2 && board.getPlayablePlaces().size() > 0){ // place parcel
            placeRandomParcel(board.getPlayablePlaces());
        }/*
        else if (nb == 3 && possibleCoordinatesCharacter().size() != 0) {
            randomMovePanda(possibleCoordinatesCharacter());
        }
        else if (possibleCoordinatesCharacter().size() != 0) {
            randomMovePeasant(possibleCoordinatesCharacter());
        }
*/
    }
}