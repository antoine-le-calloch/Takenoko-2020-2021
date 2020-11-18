package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class Coordinate {
    private int[] coordinate;

    Coordinate(int x,int y,int z){
        coordinate = new int[3];
        coordinate[0] = x;
        coordinate[1] = y;
        coordinate[2] = z;
    }

    Coordinate(Coordinate ... coordinates){
        coordinate = new int[]{0,0,0};
        for( int i = 0 ; i < coordinates[0].getCoordinate().length ; i++ ){
            for( int j = 0 ; j < coordinates.length ; j++ ){
                coordinate[i] += coordinates[j].getCoordinate()[i];
            }
        }
    }

    Coordinate(int x,int y,int z,Coordinate ... coordinates){
        coordinate = new int[3];
        coordinate[0] = x;
        coordinate[1] = y;
        coordinate[2] = z;
        for( int i = 0 ; i < coordinates[0].getCoordinate().length ; i++ ){
            for( int j = 0 ; j < coordinates.length ; j++ ){
                coordinate[i] += coordinates[j].getCoordinate()[i];
            }
        }
    }

    //return une array des coordonnées autour de la coordonnées passé en parametre
    public ArrayList<Coordinate> coordinatesAround() {
        ArrayList<Coordinate> coordinatesAround = new ArrayList<>();
        coordinatesAround.add(new Coordinate(0,-1,1,this));
        coordinatesAround.add(new Coordinate(1,-1,0,this));
        coordinatesAround.add(new Coordinate(1,0,-1,this));
        coordinatesAround.add(new Coordinate(0,1,-1,this));
        coordinatesAround.add(new Coordinate(-1,1,0,this));
        coordinatesAround.add(new Coordinate(-1,0,1,this));
        return coordinatesAround;
    }

    boolean isEqualTo(Coordinate coord){
        for( int i = 0 ; i < coordinate.length ; i++ ){
            if(coord.getCoordinate()[i] != this.getCoordinate()[i]){
                return false;
            }
        }
        return true;
    }

    static int getNorm(Coordinate coord1,Coordinate coord2){
        int norm = 0;
        for( int i = 0; i < coord1.getCoordinate().length; i++){
            norm += (coord1.getCoordinate()[i] - coord2.getCoordinate()[i]) * (coord1.getCoordinate()[i] - coord2.getCoordinate()[i]);
        }
        return norm;
    }

    int[] getCoordinate() {
        return coordinate.clone();
    }
}
