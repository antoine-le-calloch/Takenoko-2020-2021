package fr.unice.polytech.startingpoint;

import java.util.*;

abstract class Bot {
    protected final Resource resource;
    protected final Board board;
    protected final Inventory inventory;

    Bot(Resource resource, Board board) {
        this.resource = resource;
        this.board = board;
        this.inventory = new Inventory();
    }

    //Action d'un bot pendant un tour
    abstract void botPlay();

    //Pioche une mission - ACTION 1
    void drawMission(){
        inventory.addMission(resource.drawMission(MissionType.PARCEL));
    }

    //Place une parcelle à une coordonnée de la liste passée en paramètre - ACTION 2
    void placeRandomParcel(List<Coordinate> listCoord){
        Collections.shuffle(listCoord);
        board.placeParcel(resource.drawParcel(), listCoord.get(0));
    }

    //Place un canal à une coordonnée de la liste passée en paramètre - ACTION 3
    void placeRandomCanal(List<Coordinate[]> listCoord) {
        Collections.shuffle(listCoord);
        board.placeCanal(resource.drawCanal(), listCoord.get(0)[0], listCoord.get(0)[1]);
    }

    //Bouge le panda à un endroit aléatoire de la liste passée en paramètre - ACTION 4
    void randomMovePanda(List<Coordinate> listCoord) {
        Collections.shuffle(listCoord);
        int nbBamboo = board.getPlacedParcels().get(listCoord.get(0)).getNbBamboo();
        if(board.moveCharacter(board.getPanda(),listCoord.get(0))){
            if(nbBamboo>0){
                inventory.addBamboo(Color.RED);
            }
        }
    }

    //Bouge le panda à un endroit aléatoire de la liste passée en paramètre - ACTION 5
    void randomMovePeasant(List<Coordinate> listCoord) {
        Collections.shuffle(listCoord);
        if(!listCoord.isEmpty())
            board.moveCharacter(board.getPeasant(),listCoord.get(0));{
        }
    }

    //Renvoie une liste des coordonnées possibles pour les canaux
    List<Coordinate[]> possibleCoordinatesCanal(){
        Set<Coordinate[]> possibleCoordinates = new HashSet<>();
        for(Parcel parcel1 : board.getPlacedParcels().values()){
            for(Parcel parcel2 : board.getPlacedParcels().values()){
                if (board.isPlayableCanal(parcel1.getCoordinates(),parcel2.getCoordinates()))
                    possibleCoordinates.add(new Coordinate[]{parcel1.getCoordinates(),parcel2.getCoordinates()});
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    //Renvoie une liste des coordonnées possibles pour les personnages
    List<Coordinate> possibleCoordinatesCharacter(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate c : board.getPlacedParcels().keySet()) {
            if (board.isMovableCharacter(board.getPanda(),c)){
                possibleCoordinates.add(c);
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    //Renvoie l'inventaire
    Inventory getInventory(){
        return inventory;
    }
}