package fr.unice.polytech.startingpoint;

class Parcel {
    private Coordinate coordinates;

    Parcel(){
    }

    public Parcel(Coordinate coord) {
        coordinates = coord;
    }

    public void setCoordinates(Coordinate coord) {
        coordinates = coord;
    }

    public Coordinate getCoordinates(){
        return coordinates;
    }
}
