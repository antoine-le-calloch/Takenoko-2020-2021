package fr.unice.polytech.startingpoint;

class Panda extends Character {

    Panda(){
        super();
    }

    boolean action(Coordinate coordinate, Board board){
        return board.getPlacedParcels().get(coordinate).delBamboo();
    }
}
