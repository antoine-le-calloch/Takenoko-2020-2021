package fr.unice.polytech.startingpoint;

class Canal {

    private Coordinate coordinates1;
    private Coordinate coordinates2;

    Canal(){
    }

    void setCoordinates(Coordinate coord1, Coordinate coord2) {
        coordinates1 = coord1;
        coordinates2 = coord2;
    }

    Coordinate[] getCoordinatesCanal(){
        return new Coordinate[]  { coordinates1, coordinates2 };
    }


}
