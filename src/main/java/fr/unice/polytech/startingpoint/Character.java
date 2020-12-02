package fr.unice.polytech.startingpoint;

abstract class Character {
    private Coordinate coordinate;

    Character(){
        coordinate = new Coordinate(0,0,0);
    }

    void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    abstract boolean action(Coordinate coordinate, Board board);

    Coordinate getCoordinate(){
        return coordinate;
    }

}
