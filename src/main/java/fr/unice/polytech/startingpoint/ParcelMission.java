package fr.unice.polytech.startingpoint;


class ParcelMission extends Mission{
    private final String goalForm;
    private final String goalColor;

    ParcelMission(int points, String goalForm, String goalColor) {
        super(points);
        this.goalForm = goalForm;
        this.goalColor = goalColor;
    }

    //Vérifie si une mission est faite
    @Override
    int checkMission(Board board, Bot bot){
        return checkMissionParcel(board);
    }

    //Renvoie l'objectif de la mission
    String getGoal(){
        return goalForm;
    }

    //Renvoie la couleur de la mission
    String getColor() {
        return goalColor;
    }

    //Renvoie le nombre de points que les missions rapportent si elles ont été accomplies
    int checkMissionParcel(Board board) {
        switch (goalForm) {
            case "triangle":
                if (checkFormIrrigateWithColor(board,0,1))
                    return points;
                return 0;
            case "ligne":
                if (checkFormIrrigateWithColor(board,2,5))
                    return points;
                return 0;
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
                if (parcel.getColor().equals(goalColor)
                        && board.getPlacedParcels().get(coord1).getColor().equals(goalColor)
                        && board.getPlacedParcels().get(coord2).getColor().equals(goalColor))
                    return true;
            }
        }
        return false;
    }
}