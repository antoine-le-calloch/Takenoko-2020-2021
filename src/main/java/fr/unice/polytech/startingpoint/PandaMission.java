package fr.unice.polytech.startingpoint;

class PandaMission implements Mission{
    final int points;
    Color color;

    PandaMission(int points, Color color){
        this.color = color;
        this.points = points;
    }

    @Override
    public int checkMission(Board board, Bot bot) {
        return checkMissionPanda(bot);
    }

    int checkMissionPanda(Bot bot){
        if (bot.inventory.subBamboo(color)){
            return points;
        }
        return 0;
    }
}