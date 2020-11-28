package fr.unice.polytech.startingpoint;

class Parcel {
    private Coordinate coordinates;
    private boolean irrigated = false;
    private int nbBamboo = 0;
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
    Coordinate setIrrigated() {
        irrigated = true;
        return coordinates;
    }

    //ajoute un bamboo sur la parcelle
    void addBamboo(){ nbBamboo += 1;}

    //supprime un bamboo de la parcelle
    void delBamboo(){
        if (nbBamboo > 0)
            nbBamboo -= 1;
    }

    int getNbBamboo(){return nbBamboo;}

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
