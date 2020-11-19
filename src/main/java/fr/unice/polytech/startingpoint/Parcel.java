package fr.unice.polytech.startingpoint;

class Parcel {
    private Coordinate coordinates;
    private boolean irriguated=false;

    Parcel(){
    }

    Parcel(Coordinate coord) {
        coordinates = coord;
    }

    void setCoordinates(Coordinate coord) {
        coordinates = coord;
    }
    void setIrriguated() {
        irriguated=true;
    }
    boolean getIrriguated(){
        return irriguated;
    }



    Coordinate getCoordinates(){
        return coordinates;
    }
}
