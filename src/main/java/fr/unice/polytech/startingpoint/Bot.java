package fr.unice.polytech.startingpoint;

import java.util.*;

abstract class Bot {
    protected final Resource resource;
    protected final Board board;
    protected final List<Mission> inventoryMission = new ArrayList<>(); // pas de private pour les sous classes
    protected int[] inventoryBamboo = new int[] {0}; // liste de bamboo -> qu'une seule couleur pour l'instant

    Bot(Resource resource, Board board) {
        this.resource = resource;
        this.board = board;
    }

    //Action d'un bot pendant un tour
    abstract void botPlay();

    //Pioche une mission
    void drawMission(){
        inventoryMission.add(resource.drawMission("parcel"));
    }

    //Fait bouger le panda
    void movePanda(Coordinate coordinate){
        if(board.moveCharacter(board.getPanda(),coordinate)){
            addBamboo();
        }
    }

    //Fait bouger le paysan
    void movePeasant(Coordinate coordinate){
        board.moveCharacter(board.getPeasant(),coordinate);
    }

    //Ajoute un bambou à l'inventaire
    void addBamboo(){
        inventoryBamboo[0]++;
    }

    //Retire un bambou àe l'inventaire
    void deleteBamboo(){
        inventoryBamboo[0]--;
    }

    //Place une parcelle à une coordonnée de la liste passée en paramètre
    void placeRandomParcel(List<Coordinate> listCoord){
        Collections.shuffle(listCoord);
        board.placeParcel(resource.drawParcel(), listCoord.get(0));
    }

    //Place un canal à une coordonnée de la liste passée en paramètre
    void placeRandomCanal(List<Coordinate[]> listCoord) {
        Collections.shuffle(listCoord);
        board.placeCanal(resource.drawCanal(), listCoord.get(0)[0], listCoord.get(0)[1]);
    }

    //Bouge le panda à un endroit aléatoire de la liste passée en paramètre
    void randomMovePanda(List<Coordinate> listCoord) {
        Collections.shuffle(listCoord);
        int nbBamboo = board.getPlacedParcels().get(listCoord.get(0)).getNbBamboo();
        if(board.moveCharacter(board.getPanda(),listCoord.get(0))){
            if(nbBamboo>0){
                inventoryBamboo[0] ++;
            }
        };
    }

    //Bouge le panda à un endroit aléatoire de la liste passée en paramètre
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

    //Supprime la mission passée en paramètre de l'inventaire des missions
    void deleteMission(Mission mission) {
        inventoryMission.remove(mission);
    }

    //Renvoie l'inventaire de bambou
    int[] getInventoryBamboo() {
        return inventoryBamboo.clone();
    }

    //Renvoie la liste de l'inventaire des missions
    ArrayList<Mission> getInventoryMission() {
        return new ArrayList<>(inventoryMission);
    }
}