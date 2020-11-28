package fr.unice.polytech.startingpoint;

import java.util.*;

abstract class Bot {
    private final Resource resource;
    private final Board board;
    final ArrayList<Mission> inventoryMission = new ArrayList<>(); // pas de private pour les sous classes
    int[] inventoryBamboo = new int[] {0}; // liste de bamboo -> qu'une seule couleur pour l'instant

    Bot(Resource resource, Board board) {
        this.resource = resource;
        this.board = board;
    }

    //Action d'un bot pendant un tour
    abstract void botPlay();

    //Pioche une mission
    void drawMission(){
        inventoryMission.add(resource.drawMission());
    }

    void moveKoala(Coordinate coordinate){
        board.getPlacedParcels().get(coordinate).delBamboo();
        inventoryBamboo[0] += 1;
    }

    Coordinate possibleCoordinatesBamboo(){
        for(Parcel parcel1 : board.getPlacedParcels().values()) {
            if (parcel1.getNbBamboo() > 0)
                return parcel1.getCoordinates();
        }
        return null;
    }


    void deleteBamboo(){
        inventoryBamboo[0] -= 1;
    }

    //Place une parcelle à une coordonnée de la liste passé en paramètre
    void placeRandomParcel(List<Coordinate> listCoord){
        Collections.shuffle(listCoord);
        board.placeParcel(resource.drawParcel(), listCoord.get(0));
    }

    //Place un canal à une coordonnée de la liste passé en paramètre
    void placeRandomCanal(List<Coordinate[]> listCoord) {
        Collections.shuffle(listCoord);
        board.placeCanal(resource.drawCanal(), listCoord.get(0)[0], listCoord.get(0)[1]);
    }

    //Renvoie une liste des coordonnées possibles pour les canaux
    List<Coordinate[]> possibleCoordinatesCanal(){
        Set<Coordinate[]> possibleCoordinates = new HashSet<>();
        for(Parcel parcel1 : board.getPlacedParcels().values()){
            for(Parcel parcel2 : board.getPlacedParcels().values()){
                if (board.playableCanal(parcel1.getCoordinates(),parcel2.getCoordinates()))
                    possibleCoordinates.add(new Coordinate[]{parcel1.getCoordinates(),parcel2.getCoordinates()});
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    //Supprime la mission passée en paramètre de l'inventaire des missions
    void deleteMission(Mission mission) {
        inventoryMission.remove(mission);
    }


    //Renvoie la liste de l'inventaire des missions
    ArrayList<Mission> getInventoryMission() {
        return new ArrayList<>(inventoryMission);
    }
}
