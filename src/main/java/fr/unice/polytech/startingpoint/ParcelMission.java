package fr.unice.polytech.startingpoint;


class ParcelMission extends Mission{
    private final String goalForm;
    private final String goalColor;

    ParcelMission(int points, String goalForm, String goalColor) {
        super(points);
        this.goalForm = goalForm;
        this.goalColor = goalColor;
    }

    //Verifie si une mission est faite
    @Override
    int checkMission(Board board){
        return checkMissionParcel(board);
    }
    @Override
    String getGoal(){
        return goalForm;
    }

    @Override
    String getColor() {
        return goalColor;
    }

    int checkMissionParcel(Board board) {
        switch (goalForm) {
            case "triangle":
                if (checkFormIrrigateWithColor(board,0,1))
                    return points;
            case "ligne":
                if (checkFormIrrigateWithColor(board,2,5))
                    return points;
            default:
                return 0;
        }
    }

    //retourne vrai si il y a un triangle sur le plateau
    boolean checkFormIrrigateWithColor(Board board, int offset1, int offset2) {
        for (Parcel parcel : board.getPlacedParcels().values()) {
            Coordinate coord1 = new Coordinate(parcel.getCoordinates(), Coordinate.offSets().get(offset1));
            Coordinate coord2 = new Coordinate(parcel.getCoordinates(), Coordinate.offSets().get(offset2));
            if (board.getIrrigatedParcels().contains(coord1) && board.getIrrigatedParcels().contains(coord2)) {
                if (parcel.getColor().equals(goalColor) && board.getParcelByCo(coord1).getColor().equals(goalColor) && board.getParcelByCo(coord2).getColor().equals(goalColor))
                    return true;
            }
        }
        return false;
    }



}
