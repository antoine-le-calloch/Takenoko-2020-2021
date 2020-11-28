package fr.unice.polytech.startingpoint;

class Parcel {
    private Coordinate coordinates;
    private boolean irrigated = false;
    private final String color;

    Parcel(String color){
        this.color = color;
    }

    //Renvoie la parcelle après avoir fixé ses coordonnées avec les coordonnées passées en paramètre
    Parcel setCoordinates(Coordinate coordinate) {
        coordinates = coordinate;
        return this;
    }

    //Renvoie true si la parcelle vient d'être irriguée
    boolean setIrrigated() {
        if (!irrigated){
            return irrigated = true;
        }
        return false;
    }

    //Renvoie si la parcelle est irriguée ou non
    boolean getIrrigated(){
        return irrigated;
    }

    //Renvoie les coordonnées de la parcelle
    Coordinate getCoordinates(){
        return coordinates;
    }

    //Renvoie la couleur de la parcelle
    String getColor() {
        return color;
    }
}
