package fr.unice.polytech.startingpoint;

class PandaMission extends Mission{
    PandaMission(int points){
        super(points);
    }

    @Override
    int checkMission(Board board, Bot bot) {
        if(bot.getInventoryBamboo()[0] > 0) {
            bot.deleteBamboo();
            return points;
        }
        return 0;
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