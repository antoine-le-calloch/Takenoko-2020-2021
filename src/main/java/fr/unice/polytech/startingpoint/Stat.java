package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Stat {
    int[] gamesData = new int[2];

    void add(int[] data) {
        gamesData=data;
    }

    String getWinner(int[] score){
        if(score[0]==score[1])
            return "Egalité";
        return (score[0]>score[1]) ? "Joueur 1 gagne" : "Joueur 2 gagne";
    }

    public String toString(){
        return gamesData[0]+" "+gamesData[1]+"\n"+getWinner(gamesData);
    }
}
