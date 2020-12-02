package fr.unice.polytech.startingpoint;

import java.util.*;

public class Coordinate {
    private final int[] coordinate;

    Coordinate(int x,int y,int z){
        coordinate = new int[]{x,y,z};
    }

    Coordinate(Coordinate ... cs){
        coordinate = new int[]{0,0,0};
        for(int i = 0; i < coordinate.length; i++){
            for(Coordinate c : cs){
                coordinate[i] += c.getCoordinate()[i];
            }
        }
    }

    //Renvoie si la coordonnée actuelle et la coordonnée passée en paramètre sont à côté l'un de l'autre
    boolean isNextTo(Coordinate c){
        return getNorm(c,this) == 2;
    }

    //Renvoie si la coordonnée actuelle est centrale
    boolean isCentral() {
        return this.equals(new Coordinate(0,0,0));
    }

    //Renvoie true si les coordonnées sont sur la même ligne
    boolean isOnTheSameLine(Coordinate c) {
        int nbSameCoordinate = 0;
        for(int i = 0; i < coordinate.length ; i++){
            if(c.coordinate[i] == coordinate[i]){
                nbSameCoordinate++;
            }
        }
        return nbSameCoordinate == 1;
    }

    //Renvoie l'opposé de la coordonnée actuelle
    Coordinate negative() {
        Coordinate c = new Coordinate(this);
        for(int i = 0 ; i < coordinate.length ; i++){
            c.coordinate[i] = -c.coordinate[i];
        }
        return c;
    }

    //Renvoie un clone des coordonnées sous forme d'une liste d'entiers
    int[] getCoordinate() {
        return coordinate.clone();
    }

    //Renvoie une liste des coordonnées autour de la coordonnée actuelle
    List<Coordinate> coordinatesAround() {
        ArrayList<Coordinate> coordinatesAround = new ArrayList<>();
        for (Coordinate offSet : offSets())
            coordinatesAround.add(new Coordinate(this,offSet));
        return coordinatesAround;
    }

    //Renvoie les coordonnées entre deux coordonnées si elles sont sur la même ligne
    static List<Coordinate> getAllCoordinatesBetween(Coordinate coordinate1, Coordinate coordinate2) {
        if(coordinate1.isOnTheSameLine(coordinate2)){
            Coordinate unitVector = getUnitVector(coordinate1,coordinate2);
            Coordinate coordinate = new Coordinate(coordinate1,unitVector);
            List<Coordinate> coordinateBetween = new ArrayList<>();
            while(!coordinate.equals(coordinate2)){
                coordinateBetween.add(coordinate);
                coordinate = new Coordinate(coordinate1,unitVector);
            }
            return coordinateBetween;
        }
        return new ArrayList<>();
    }

    //Renvoie une liste des coordonnées en commun autour des deux coordonnées passées en paramètre  STATIC
    static List<Coordinate> getInCommonAroundCoordinates(Coordinate c1, Coordinate c2){
        List<Coordinate> inCommonCoordinates = new ArrayList<>(c1.coordinatesAround());
        inCommonCoordinates.retainAll(c2.coordinatesAround());
        return inCommonCoordinates;
    }

    //Renvoie le vecteur unitaire entre les deux coordonnées
    static Coordinate getUnitVector(Coordinate c1, Coordinate c2){
        Coordinate vector = getVector(c1, c2);
        Coordinate unitVector = new Coordinate(0,0,0);
        int lowestDistance = getNorm(vector, new Coordinate(0,0,0));
        for(Coordinate offSet : Coordinate.offSets()){
            if(getNorm(vector,offSet) < lowestDistance){
                lowestDistance = getNorm(vector,offSet);
                unitVector = offSet;
            }
        }
        return unitVector;
    }

    //Renvoie le vecteur liant les deux coordonnées
    static Coordinate getVector(Coordinate c1,Coordinate c2){
        return new Coordinate(c1,c2.negative());
    }

    //Renvoie la norme au carré du vecteur reliant les deux coordonnées passées en paramètre  STATIC
    static int getNorm(Coordinate c1,Coordinate c2){
        int norm = 0;
        for( int i = 0; i < c1.getCoordinate().length; i++){
            norm += (c1.getCoordinate()[i] - c2.getCoordinate()[i]) * (c1.getCoordinate()[i] - c2.getCoordinate()[i]);
        }
        return norm;
    }

    //Renvoie une liste des offSets possibles autour d'une coordonnée  STATIC
    static List<Coordinate> offSets() {
        List<Coordinate> offSets = new ArrayList<>();
        offSets.add(new Coordinate(1, 0, -1)); //0-2h
        offSets.add(new Coordinate(1, -1, 0)); //2-4h
        offSets.add(new Coordinate(0, -1, 1)); //4-6h
        offSets.add(new Coordinate(-1, 0, 1)); //6-8h
        offSets.add(new Coordinate(-1, 1, 0)); //8-10h
        offSets.add(new Coordinate(0, 1, -1)); //10-12h
        return offSets;
    }

    //Renvoie un SortedSet contenant les coordonnées passées en paramètre avec l'outil de comparaison mis à jour pour le type des coordonnées
    static SortedSet<Coordinate> getSortedSet(Coordinate c1, Coordinate c2){
        SortedSet<Coordinate> sortedSet = new TreeSet<>(new Comparator<Coordinate>(){
            @Override
            public int compare(Coordinate o1, Coordinate o2) {
                for(int i = 0 ; i < o1.coordinate.length ; i++){
                    if(o1.coordinate[i] > o2.coordinate[i])
                        return 1;
                    if(o1.coordinate[i] < o2.coordinate[i])
                        return -1;
                }
                return 0;
            }
        });
        sortedSet.addAll(Arrays.asList(c1,c2));
        return sortedSet;
    }

    //Renvoie true si les deux coordonnées sont identiques
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Coordinate))
            return false;
        Coordinate co = (Coordinate) obj;
        return Arrays.equals(coordinate, co.coordinate);
    }

    //Renvoie le hashCode de la liste des coordonnées
    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinate);
    }

    //Renvoie l'objet sous form de String
    public String toString(){
        return "["+coordinate[0]+","+coordinate[1]+","+coordinate[2]+"]";
    }
}