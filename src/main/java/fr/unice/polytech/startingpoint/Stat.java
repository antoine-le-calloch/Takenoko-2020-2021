package fr.unice.polytech.startingpoint;

class Stat {
    private final int NBP = 2;
    private final int[] points = new int[NBP];
    private final int[] winRate = new int[NBP];

    Stat(){
    }

    void add(int[] data) {
        getWinner(data);
        for (int i = 0; i < NBP; i++) {
            points[i] += data[i];
        }
    }

    //return le message final
    void getWinner(int[] score){
        if(score[0]>score[1]) {
            winRate[0]++;
        }
        else if (score[0]<score[1]){
            winRate[1]++;
        }
    }

    //affiche le message
    public String toString(){
        return  "Joueur 1 : " + winRate[0]/10.0 + "% win rate with a " + points[0]/1000.0 + " points average\n" +
                "Joueur 2 : " + winRate[1]/10.0 + "% win rate with a " + points[1]/1000.0 + " points average";
    }
}
