package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Coordinate {
    private final int[] coordinate;

    Coordinate(int x,int y,int z){
        coordinate = new int[]{x,y,z};
    }

    Coordinate(Coordinate ... coordinates){
        coordinate = new int[]{0,0,0};
        for(int i = 0; i < coordinate.length; i++){
            for(Coordinate coord : coordinates){
                coordinate[i] += coord.getCoordinate()[i];
            }
        }
    }

    //return une array des coordonnées autour de la coordonnées passé en parametre  STATIC
    ArrayList<Coordinate> coordinatesAround() {
        ArrayList<Coordinate> coordinatesAround = new ArrayList<>();
        for (Coordinate offSet : offSets())
            coordinatesAround.add(new Coordinate(this,offSet));
        return coordinatesAround;
    }

    int[] getCoordinate() {
        return coordinate.clone();
    }

    boolean isCentral() {
        return this.equals(new Coordinate(0,0,0));
    }

    static int getNorm(Coordinate coord1,Coordinate coord2){
        int norm = 0;
        for( int i = 0; i < coord1.getCoordinate().length; i++){
            norm += (coord1.getCoordinate()[i] - coord2.getCoordinate()[i]) * (coord1.getCoordinate()[i] - coord2.getCoordinate()[i]);
        }
        return norm;
    }

    static List<Coordinate> offSets() {
        List<Coordinate> offSets = new ArrayList<>();
        offSets.add(new Coordinate(1, -1, 0)); //0-2h
        offSets.add(new Coordinate(1, 0, -1)); //2-4h
        offSets.add(new Coordinate(0, 1, -1)); //4-6h
        offSets.add(new Coordinate(-1, 1, 0)); //6-8h
        offSets.add(new Coordinate(-1, 0, 1)); //8-10h
        offSets.add(new Coordinate(0, -1, 1)); //10-12h
        return offSets;
    }

    static boolean coNextToEachother(Coordinate coord1,Coordinate coord2){
        return getNorm(coord1, coord2) == 2;
    }

    static boolean isNextoCentral(Coordinate coord){
        return getNorm(coord,new Coordinate(0,0,0)) == 2;
    }

    @Override //remplace le equals - il verifie maintenant ce qu'on veut
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (!(obj instanceof Coordinate))
            return false;
        Coordinate co = (Coordinate) obj;
        return Arrays.equals(coordinate, co.coordinate);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinate);
    }

    public String toString(){
        return coordinate[0] + " " +coordinate[1] +" "+ coordinate[2] ;
    }
}
