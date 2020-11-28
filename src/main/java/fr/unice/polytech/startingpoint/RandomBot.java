package fr.unice.polytech.startingpoint;

import java.util.Random;

class RandomBot extends Bot {
    Random random;
    Resource resource;
    Board board;

    RandomBot(Resource resource, Board board) {
        super(resource, board);
        this.resource = resource;
        this.board = board;
        random = new Random();
    }

    //Action d'un bot pendant un tour
    @Override
    void botPlay(){
        int nb = random.nextInt(3);
        if (nb == 0 && resource.getMission().size() > 0) {
            drawMission();
        }
        else if (nb ==1 && resource.getCanal().size() > 0) {
            if (possibleCoordinatesCanal().size() > 0)
                placeRandomCanal(possibleCoordinatesCanal());
        }
        else if (board.getPlayablePlaces().size() > 0){
            placeRandomParcel(board.getPlayablePlaces());// placera aléatoirement une parcelle sur une case libre
        }
        if(resource.getCanal().size()>0 && possibleCoordinatesCanal().size()>0)
            placeRandomCanal(possibleCoordinatesCanal());
    }
}