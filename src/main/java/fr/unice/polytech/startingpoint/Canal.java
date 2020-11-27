package fr.unice.polytech.startingpoint;

class Canal {
    private Coordinate coordinates1;
    private Coordinate coordinates2;

    Canal(){}

    Canal setCoordinates(Coordinate coord1, Coordinate coord2) {
        coordinates1 = coord1;
        coordinates2 = coord2;
        return this;
    }

    Coordinate[] getCoordinatesCanal(){
        return new Coordinate[]  { coordinates1, coordinates2 };
    }

    boolean sameDoubleCoordinates(Coordinate coord1, Coordinate coord2){
        return (coordinates1.equals(coord1) && coordinates2.equals(coord2)) || (coordinates1.equals(coord2) && coordinates2.equals(coord1));
    }
}
