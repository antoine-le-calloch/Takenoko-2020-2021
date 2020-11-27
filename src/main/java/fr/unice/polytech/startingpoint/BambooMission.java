package fr.unice.polytech.startingpoint;

class BambooMission extends Mission{

    BambooMission(int points){
        super(points);
    }

    @Override
    int checkMission(Board board) {
        return points;
    }

    @Override
    String getGoal() {
        return "null";
    }

    @Override
    String getColor() {
        return "null";
    }
}
