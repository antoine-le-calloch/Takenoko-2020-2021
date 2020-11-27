package fr.unice.polytech.startingpoint;

class Parcel {
    private Coordinate coordinates;
    private boolean irrigated = false;
    private final String color;

    Parcel(String color){
        this.color = color;
    }

    Parcel setCoordinates(Coordinate coordinate) {
        coordinates = coordinate;
        return this;
    }

    boolean setIrrigated() {
        if (!irrigated){
            return irrigated = true;
        }
        return false;
    }

    boolean getIrrigated(){
        return irrigated;
    }

    Coordinate getCoordinates(){
        return coordinates;
    }

    String getColor() {
        return color;
    }
}
