package fr.unice.polytech.startingpoint;

class PandaMission extends Mission{
    String color;
    PandaMission(int points, String color){
        super(points);
        this.color = color;
    }

    @Override
    int checkMission(Board board, Bot bot) {
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