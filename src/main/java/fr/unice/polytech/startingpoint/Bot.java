package fr.unice.polytech.startingpoint;

import java.util.*;

abstract class Bot {
    private final Resource resource;
    private final Board board;
    final ArrayList<Mission> inventoryMission = new ArrayList<>(); // pas de private pour les sous classes

    Bot(Resource resource, Board board) {
        this.resource = resource;
        this.board = board;
    }

    //Action d'un bot pendant un tour
    abstract void botPlay();

    //pioche une mission
    void drawMission(){
        inventoryMission.add(resource.drawMission());
    }


    //place une parcelle dans une coordonnée de la liste donnée
    void placeRandomParcel(ArrayList<Coordinate> listCoord){
        Collections.shuffle(listCoord);
        board.placeParcel(resource.drawParcel(), listCoord.get(0));
    }

    //place un canal sur une coordonnée contenue dans une liste donnée
    void placeRandomCanal(ArrayList<Coordinate[]> listCoord) {
        Collections.shuffle(listCoord);
        board.putCanal(resource.drawCanal(), listCoord.get(0)[0], listCoord.get(0)[1]);
    }

    ArrayList<Coordinate[]> possibleCoordinatesCanal(){
        Set<Coordinate[]> possibleCoordinates = new HashSet<>();
        for(Parcel parcel1 : board.getPlacedParcels()){
            for(Parcel parcel2 : board.getPlacedParcels()){
                if (board.playableCanal(parcel1.getCoordinates(),parcel2.getCoordinates()))
                    possibleCoordinates.add(new Coordinate[] {parcel1.getCoordinates(),parcel2.getCoordinates()});
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    void deleteMission(Mission mission) {
        inventoryMission.remove(mission);
    }

    ArrayList<Mission> getInventoryMission() {
        return new ArrayList<>(inventoryMission);
    }
}
