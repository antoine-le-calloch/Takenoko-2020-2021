package fr.unice.polytech.startingpoint;

class PandaMission implements Mission{
    final int points;
    String color;

    PandaMission(int points, String color){
        this.color = color;
        this.points = points;
    }

    @Override
    public int checkMission(Board board, Bot bot) {
        return checkMissionPanda(bot);
    }

    int checkMissionPanda(Bot bot){
        switch (color) {
            case "red":
                if (bot.getInventoryBamboo()[0] > 0) {
                    bot.deleteBamboo(0);
                    return points;
                }
                return 0;
            case "blue":
                if (bot.getInventoryBamboo()[1] > 0) {
                    bot.deleteBamboo(1);
                    return points;
                }
                return 0;
            default: return 0;
        }
    }

}