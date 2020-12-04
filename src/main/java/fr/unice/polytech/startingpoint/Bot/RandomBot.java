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
    private final Random random;
    private final Random random2;

    public RandomBot(Resource resource, Board board) {
        super(resource, board);
        random = new Random();
        random2 = new Random();
    }

    //Action d'un bot pendant un tour
    @Override
    public void botPlay(){
        int nb = random.nextInt(5);

        if (nb == 0 && resource.getNbMission() > 0) {// pioche mission
            int nb2 = random2.nextInt(3);

            if (nb2 == 0 && resource.getDeckParcelMission().size() > 0)
                drawMission(MissionType.PARCEL);
            if (nb2 == 1 && resource.getDeckPandaMission().size() > 0)
                drawMission(MissionType.PANDA);
            if (nb2 == 2 && resource.getDeckPeasantMission().size() > 0)
                drawMission(MissionType.PEASANT);
        }

        else if (nb == 1 && resource.getCanal().size() > 0 && resource.getCanal().size() > 0) {  // place canal
            if (possibleCoordinatesCanal().size() > 0) {
                List<Coordinate[]> list = possibleCoordinatesCanal();
                Collections.shuffle(list);
                placeCanal(list.get(0));
            }
        }

        else if (nb == 2 && possibleCoordinatesParcel().size() > 0 && resource.getParcel().size() > 0){ // place parcel
            Parcel parcel = drawParcel();
            List<Coordinate> list = possibleCoordinatesParcel();
            Collections.shuffle(list);
            placeParcel(list.get(0), parcel);
        }

        else if (nb == 3 && possibleCoordinatesCharacter().size() != 0) {
            List<Coordinate> list = possibleCoordinatesCharacter();
            Collections.shuffle(list);
            movePanda(list.get(0));
        }

        else if (possibleCoordinatesCharacter().size() != 0 ) {
            List<Coordinate> list = possibleCoordinatesParcel();
            Collections.shuffle(list);
            movePeasant(list.get(0));
        }
    }
}