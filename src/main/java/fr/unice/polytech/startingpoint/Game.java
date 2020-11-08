package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Game {
    Resource resource = new Resource();
    Board board = new Board();
    ArrayList<Bot> botList = new ArrayList<>();
    int[] scores;

    Game(int[] numBots){

        scores = new int[numBots.length];
        for(int i = 0; i < numBots.length; i++){
            botList.add(new Bot(numBots[i]));
        }
    }

    void play() {

        int numBot = 0;
        boolean isContinue=true;
        while(isContinue) {

            botList.get(numBot).play(resource, board);
            numBot=(numBot+1)%2;

            if (botList.get(numBot).mission_done == 1)
                isContinue=false;

        }
        for (Bot bot : botList){
            scores[bot.num_bot]=bot.score_bot;
        }
        System.out.println(scores[0] + " " + scores[1]);

    }

    int[] getData() {
        return scores;
    }
}