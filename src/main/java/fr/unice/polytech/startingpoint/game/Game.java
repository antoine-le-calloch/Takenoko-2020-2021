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
    private final Resource resource;
    private final Board board;
    private final Rules rules;
    private TemporaryInventory temporaryInventory;
    private final PlayerData playerData;

    //Normal Constructor
    Game(BotType[] botTypes,int nbMission){
        resource = new Resource();
        board = new Board();
        rules = new Rules(resource,board);
        playerData = new PlayerData(botTypes, this,nbMission);
        temporaryInventory = new TemporaryInventory(2);
    }

    //Test Constructor
    Game(){
        resource = new Resource();
        board = new Board();
        rules = new Rules(resource,board);
        playerData = new PlayerData(new BotType[]{BotType.PARCELBOT}, this,0);
        temporaryInventory = new TemporaryInventory();
    }

    // Chaque bot joue tant que isContinue est true, et on verifie le nombre de mission faite à chaque tour
    void play() {
        while(playerData.isContinue() && (!rules.isEmpty())) {
            temporaryInventory = new TemporaryInventory(1);
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

    PlayerData getPlayerData() {
        return playerData;
    }

    List<Integer> getScores(){
        return playerData.getScores();
    }

    /**
     * <h1><u>BOT INTERACTIONS</u></h1>
     */

    public void drawCanal() throws OutOfResourcesException {
        temporaryInventory.looseStamina();
        playerData.addCanal(resource.drawCanal());
    }

    public void drawMission(MissionType missionType) throws OutOfResourcesException {
        temporaryInventory.looseStamina();
        playerData.addMission(resource.drawMission(missionType));
    }

    public List<ColorType> drawParcels() throws IllegalAccessException, OutOfResourcesException {
        if (!temporaryInventory.hasDrawn()){
            temporaryInventory.looseStamina();
            temporaryInventory.saveParcels(resource.drawParcel());
            List<ColorType> colorTypeList = new ArrayList<>();
            for(Parcel parcel : temporaryInventory.getParcelsSaved()){
                colorTypeList.add(parcel.getColor());
            }
            return colorTypeList;
        }
        throw new IllegalAccessException("Already used this method.");
    }

    public void selectParcel(ColorType colorType){
        for (Parcel parcel : temporaryInventory.getParcelsSaved()){
            if (parcel.getColor() == colorType){
                temporaryInventory.add(parcel);
                return;
            }
        }
        throw new IllegalArgumentException("Wrong Parcel asked.");
    }

    public void placeParcel(Coordinate coordinate) throws BadPlaceParcelException {
        if(rules.isPlayableParcel(coordinate)){
            board.placeParcel(temporaryInventory.getParcel(),coordinate);
        }
        else
            throw new BadPlaceParcelException(coordinate);
    }

    public void placeCanal(Coordinate coordinate1, Coordinate coordinate2) throws BadPlaceCanalException, OutOfResourcesException {
        if (rules.isPlayableCanal(coordinate1, coordinate2))
            board.placeCanal(playerData.getInventory().pickCanal(), coordinate1, coordinate2);
        else
            throw new BadPlaceCanalException(coordinate1, coordinate2);
    }

    public void moveCharacter(CharacterType characterType, Coordinate coordinate) throws BadMoveCharacterException, OutOfResourcesException {
        if(rules.isMovableCharacter(characterType,coordinate)){
            temporaryInventory.looseStamina();
            playerData.addBamboo(board.moveCharacter(characterType, coordinate));
        }
        else
            throw new BadMoveCharacterException(coordinate);
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
            case PEASANT_MISSION:
                return resource.getDeckPeasantMission().size();
            case PANDA_MISSION:
                return resource.getDeckPandaMission().size();
            case PARCEL_MISSION:
                return resource.getDeckParcelMission().size();
            case PARCEL:
                return resource.getDeckParcel().size();
            case CANAL:
                return resource.getDeckCanal().size();
            case ALL_MISSION:
                return resource.getNbMission();
            default:
                throw new IllegalArgumentException("Wrong ResourceType.");
        }

    }
}