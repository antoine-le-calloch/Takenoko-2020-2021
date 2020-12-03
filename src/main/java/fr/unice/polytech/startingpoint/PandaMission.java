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
        switch (color) {
            case RED:
                if (bot.getInventoryBamboo()[0] > 0) {
                    bot.deleteBamboo(0);
                    return points;
                }
                return 0;
            case BLUE:
                if (bot.getInventoryBamboo()[1] > 0) {
                    bot.deleteBamboo(1);
                    return points;
                }
                return 0;
        }
        return 0;
    }
}