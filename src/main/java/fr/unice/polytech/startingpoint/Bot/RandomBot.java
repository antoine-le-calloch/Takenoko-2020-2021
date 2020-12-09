package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.Board;
import fr.unice.polytech.startingpoint.Game.Coordinate;
import fr.unice.polytech.startingpoint.Game.Parcel;
import fr.unice.polytech.startingpoint.Game.Resource;
import fr.unice.polytech.startingpoint.Type.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Classe qui represente un bot qui joue aleatoirement
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class RandomBot extends Bot {
    private Random random;
    private Random random2;

    public RandomBot(Resource resource, Board board) {
        super(resource, board);
        random = new Random();
        random2 = new Random();
    }

    public void setRand(Random rand1, Random rand2){
        random = rand1;
        random2 = rand2;
    }

    //Action d'un bot pendant un tour
    @Override
    public void botPlay(){
        int randAction = random.nextInt(5);

        if (randAction == 0 && resource.getNbMission() > 0) {// pioche mission
            int randMission = random2.nextInt(3);

            if (randMission == 0 && resource.getDeckParcelMission().size() > 0)
                drawMission(MissionType.PARCEL);
            if (randMission == 1 && resource.getDeckPandaMission().size() > 0)
                drawMission(MissionType.PANDA);
            if (randMission == 2 && resource.getDeckPeasantMission().size() > 0)
                drawMission(MissionType.PEASANT);
        }

        else if (randAction == 1 && resource.getCanal().size() > 0 && resource.getCanal().size() > 0) {  // place canal
            if (possibleCoordinatesCanal().size() > 0) {
                List<Coordinate[]> list = possibleCoordinatesCanal();
                Collections.shuffle(list);
                placeCanal(list.get(0));
            }
        }

        else if (randAction == 2 && possibleCoordinatesParcel().size() > 0 && resource.getParcel().size() > 0){ // place parcel
            List<Parcel> parcelList = drawParcel();
            Collections.shuffle(parcelList);
            List<Coordinate> list = possibleCoordinatesParcel();
            Collections.shuffle(list);
            placeParcel(list.get(0), parcelList.get(0));
        }

        else if (randAction == 3 && possibleCoordinatesPanda().size() != 0) {
            List<Coordinate> list = possibleCoordinatesPanda();
            Collections.shuffle(list);
            movePanda(list.get(0));
        }

        else if (possibleCoordinatesPeasant().size() != 0 ) {
            List<Coordinate> list = possibleCoordinatesPeasant();
            Collections.shuffle(list);
            movePeasant(list.get(0));
        }
    }
}