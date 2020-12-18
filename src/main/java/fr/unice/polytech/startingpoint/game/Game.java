package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;
import fr.unice.polytech.startingpoint.exception.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Moteur de jeu, creation d'une partie, fait jouer les bots, verifie les missions faites et termine la partie
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class Game{
    private Resource resource;
    private Board board;
    private Rules rules;
    private final TemporaryInventory temporaryInventory;
    private final PlayerData playerData;

    //Normal Constructor
    Game(BotType[] botTypes,int nbMission){
        initializeGame();
        playerData = new PlayerData(botTypes, this,nbMission);
        temporaryInventory = new TemporaryInventory(2);
    }

    //Test Constructor
    Game(){
        initializeGame();
        playerData = new PlayerData(new BotType[]{BotType.Random}, this,0);
        temporaryInventory = new TemporaryInventory();
    }

    private void initializeGame(){
        resource = new Resource();
        board = new Board();
        rules = new Rules(resource,board);
    }

    // Chaque bot joue tant que isContinue est true, et on verifie le nombre de mission faite à chaque tour
    void play() {
        while(playerData.isContinue() && (!resource.isEmpty())) {
            temporaryInventory.reset(2);
            playerData.getBot().botPlay();
            temporaryInventory.hasPlayedCorrectly();
            playerData.missionDone();
            playerData.nextBot();
        }
    }

    Board getBoard() {
        return board;
    }

    Resource getResource() {
        return resource;
    }

    Rules getRules() {
        return rules;
    }

    TemporaryInventory getTemporaryInventory() {
        return temporaryInventory;
    }

    PlayerData getPlayerData() {
        return playerData;
    }

    List<Integer> getScores(){
        return playerData.getScores();
    }

    /**
     * <h1><u>BOT INTERACTIONS</u></h1>
     */

    public void drawCanal() throws OutOfResourcesException, IllegalAccessException {
        if (temporaryInventory.add(ActionType.DrawCanal)){
            temporaryInventory.looseStamina();
            playerData.addCanal(resource.drawCanal());
        }
        else
            throw new IllegalAccessException("Already used this method.");
    }

    public void drawMission(MissionType missionType) throws OutOfResourcesException, IllegalAccessException {
        if (temporaryInventory.add(ActionType.DrawMission)){
            temporaryInventory.looseStamina();
            playerData.addMission(resource.drawMission(missionType));
        }
        else
            throw new IllegalAccessException("Already used this method.");
    }

    public List<ColorType> drawParcels() throws IllegalAccessException, OutOfResourcesException {
        if (temporaryInventory.add(ActionType.DrawParcels)){
            temporaryInventory.looseStamina();
            temporaryInventory.saveParcels(resource.drawParcels());
            List<ColorType> colorTypeList = new ArrayList<>();
            for(Parcel parcel : temporaryInventory.getParcelsSaved()){
                colorTypeList.add(parcel.getColor());
            }
            return colorTypeList;
        }
        else
            throw new IllegalAccessException("Already used this method.");
    }

    public void selectParcel(ColorType colorType) throws IllegalAccessException{
        if (temporaryInventory.add(ActionType.SelectParcel)){
            if (temporaryInventory.contains(ActionType.DrawParcels)){
                for (Parcel parcel : temporaryInventory.getParcelsSaved()){
                    if (parcel.getColor() == colorType){
                        resource.selectParcel(parcel);
                        temporaryInventory.saveParcel(parcel);
                        return;
                    }
                }
                throw new IllegalArgumentException("Wrong Parcel asked.");
            }
            else
                throw new IllegalAccessException("You haven’t drawn.");
        }
        else
            throw new IllegalAccessException("Already used this method.");
    }

    public void placeParcel(Coordinate coordinate) throws BadPlaceParcelException, IllegalAccessException{
        if (temporaryInventory.add(ActionType.PlaceParcel)){
            if (temporaryInventory.contains(ActionType.DrawParcels) && temporaryInventory.contains(ActionType.SelectParcel)){
                if(rules.isPlayableParcel(coordinate)){
                    board.placeParcel(temporaryInventory.getParcel(),coordinate);
                }
                else{
                    temporaryInventory.remove(ActionType.PlaceParcel);
                    throw new BadPlaceParcelException(coordinate);
                }
            }
            else
                throw new IllegalAccessException("You haven’t drawn or selected a parcel.");
        }
        else
            throw new IllegalAccessException("Already used this method.");
    }

    public void placeCanal(Coordinate coordinate1, Coordinate coordinate2) throws BadPlaceCanalException, OutOfResourcesException, IllegalAccessException {
        if (temporaryInventory.add(ActionType.PlaceCanal)) {
            if (rules.isPlayableCanal(coordinate1, coordinate2))
                board.placeCanal(playerData.getInventory().pickCanal(), coordinate1, coordinate2);
            else{
                temporaryInventory.remove(ActionType.PlaceCanal);
                throw new BadPlaceCanalException(coordinate1, coordinate2);
            }
        }
        else
            throw new IllegalAccessException("Already used this method.");
    }

    public void moveCharacter(CharacterType characterType, Coordinate coordinate) throws BadMoveCharacterException, OutOfResourcesException, IllegalAccessException {
        if (temporaryInventory.add(ActionType.get(characterType))) {
            if (rules.isMovableCharacter(characterType, coordinate)) {
                temporaryInventory.looseStamina();
                try {
                    board.moveCharacter(characterType, coordinate);
                    playerData.addBamboo(board.getPlacedParcels().get(coordinate).getColor());
                }
                catch (CantDeleteBambooException ignored) {
                }
            }
            else{
                temporaryInventory.remove(ActionType.get(characterType));
                throw new BadMoveCharacterException(coordinate);
            }
        }
        else
            throw new IllegalAccessException("Already used this method.");
    }

    /**
     * <h1><u>BOT GETTERS</u></h1>
     */

    public boolean isPlacedParcel(Coordinate coordinate) {
        return board.isPlacedParcel(coordinate);
    }

    public boolean isIrrigatedParcel(Coordinate coordinate) {
        return board.isPlacedAndIrrigatedParcel(coordinate);
    }

    public ColorType getPlacedParcelsColor(Coordinate coordinate) {
        return board.getPlacedParcels().get(coordinate).getColor();
    }

    public int getPlacedParcelsNbBamboo(Coordinate coordinate) {
        return board.getPlacedParcels().get(coordinate).getNbBamboo();
    }

    public List<Coordinate> getPlacedCoordinates(){
        return new ArrayList<>(board.getPlacedParcels().keySet());
    }

    public List<ParcelMission> getInventoryParcelMission() {
        return playerData.getParcelMissions();
    }

    public List<PandaMission> getInventoryPandaMission() {
        return playerData.getPandaMissions();
    }

    public List<PeasantMission> getInventoryPeasantMission() {
        return playerData.getPeasantMissions();
    }

    public List<Mission> getInventoryMission() {
        return playerData.getMissions();
    }

    public int getResourceSize(ResourceType resourceType){
        switch (resourceType){
            case PeasantMission:
                return resource.getDeckPeasantMission().size();
            case PandaMission:
                return resource.getDeckPandaMission().size();
            case ParcelMission:
                return resource.getDeckParcelMission().size();
            case Parcel:
                return resource.getDeckParcel().size();
            case Canal:
                return resource.getDeckCanal().size();
            case AllMission:
                return resource.getNbMission();
            default:
                throw new IllegalArgumentException("Wrong ResourceType.");
        }

    }
}