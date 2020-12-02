package fr.unice.polytech.startingpoint;

class Peasant extends Character {

    Peasant(){
        super();
    }

    boolean action(Coordinate coordinate, Board board){
        return board.getPlacedParcels().get(coordinate).addBamboo();
    }
}
