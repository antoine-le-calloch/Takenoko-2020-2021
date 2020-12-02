package fr.unice.polytech.startingpoint;

class PeasantMission extends Mission{
    PeasantMission(int points){
        super(points);
    }

    @Override
    int checkMission(Board board, Bot bot) {
        return checkMissionPeasant(board);
    }

    int checkMissionPeasant(Board board) {
        for (Parcel parcel : board.getPlacedParcels().values()) {
            if (parcel.getNbBamboo() == 2)
                return points;
        }
        return 0;
    }
}