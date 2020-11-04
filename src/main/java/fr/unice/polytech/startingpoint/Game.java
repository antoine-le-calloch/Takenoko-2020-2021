package fr.unice.polytech.startingpoint;

public class Game {
    GameState gameState = new GameState();
    Bot []player = new Bot[2];
    int []tabData = new int[2];

    Game(int num_bot0, int num_bot1){
        player[0] = new Bot(num_bot0, gameState);
        player[1] = new Bot(num_bot1, gameState);
    }

    void play() {
        int num = 0;

        while(player[num].play()){
            num=(num+1)%2;
        }
        player[num].play();

        tabData[0] = player[0].score();
        tabData[1] = player[1].score();
    }

    int[] getData() {
        return tabData;
    }
}