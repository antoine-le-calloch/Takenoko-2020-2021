package fr.unice.polytech.startingpoint;

class Parcel {
    private int[] coordinates = new int[3];

    Parcel(){
    }

    public Parcel(int x, int y, int z) {
        coordinates[0] = x;
        coordinates[1] = y;
        coordinates[2] = z;
    }

    public void setPosition(int x, int y, int z) {
        coordinates[0] = x;
        coordinates[1] = y;
        coordinates[2] = z;
    }

    public int[] getCoordinates(){
        return coordinates.clone();
    }

    public int getNorm(Parcel placedParcel) {
        int norm = 0;
        for(int i = 0 ; i < coordinates.length ; i++){
            norm += (coordinates[i] - placedParcel.coordinates[i])*(coordinates[i] - placedParcel.coordinates[i]);
        }
        return norm;
    }
}
