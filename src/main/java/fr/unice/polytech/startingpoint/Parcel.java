package fr.unice.polytech.startingpoint;

class Parcel {
    private int[] coordinates = new int[3];

    Parcel(){
    }

    public Parcel(int[] coord) {
        coordinates[0] = coord[0];
        coordinates[1] = coord[1];
        coordinates[2] = coord[2];
    }

    public void setPosition(int[] coord) {
        coordinates[0] = coord[0];
        coordinates[1] = coord[1];
        coordinates[2] = coord[2];
    }

    public int[] getCoordinates(){
        return coordinates.clone();
    }
}
