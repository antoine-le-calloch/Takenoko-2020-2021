package fr.unice.polytech.startingpoint;

class Parcel {
    private Coordinate coordinates;
    private boolean irrigated=false;
    private final String color;

    Parcel(String color){
        this.color = color;
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

    String getColor() { return color;}
}
