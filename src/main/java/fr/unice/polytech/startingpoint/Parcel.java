package fr.unice.polytech.startingpoint;

class Parcel {
    private Coordinate coordinates;
    private boolean irrigated=false;

    Parcel(){
    }

    Parcel(Coordinate coord) {
        coordinates = coord;
    }

    void setCoordinates(Coordinate coord) {
        coordinates = coord;
    }
    void setIrrigated() {
        irrigated=true;
    }
    boolean getIrrigated(){
        return irrigated;
    }

    Coordinate getCoordinates(){
        return coordinates;
    }
}
