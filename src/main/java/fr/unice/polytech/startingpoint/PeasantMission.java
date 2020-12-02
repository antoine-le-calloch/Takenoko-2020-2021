package fr.unice.polytech.startingpoint;

class PeasantMission implements Mission{
    final int points;
    PeasantMission(int points){
        this.points = points;
    }

    @Override
    public int checkMission(Board board, Bot bot) {
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