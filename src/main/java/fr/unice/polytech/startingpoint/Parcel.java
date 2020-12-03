package fr.unice.polytech.startingpoint;

class Parcel {
    private Coordinate coordinates;
    private boolean irrigated = false;
    private int nbBamboo = 0;
    private final Color color;

    Parcel(Color color){
        this.color = color;
    }

    //Renvoie la parcelle après avoir fixé ses coordonnées avec les coordonnées passées en paramètre
    Parcel setCoordinates(Coordinate coordinate) {
        coordinates = coordinate;
        return this;
    }

    //Renvoie les coordonnées de la parcelle après l'avoir irrigué et lui avoir ajouté un bambou si elle ne l'était pas avant
    Coordinate setIrrigated() {
        if(!irrigated)
            addBamboo();
        irrigated = true;
        return coordinates;
    }

    //Ajoute un bamboo à la parcelle
    boolean addBamboo(){
        if (nbBamboo < 4){
            nbBamboo ++;
            return true;
        }
        return false;
    }

    //Supprime un bambou de la parcelle
    boolean delBamboo(){
        if (nbBamboo > 0){
            nbBamboo --;
            return true;
        }
        return false;
    }

    int getNbBamboo(){
        return nbBamboo;
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
    Color getColor() {
        return color;
    }
}