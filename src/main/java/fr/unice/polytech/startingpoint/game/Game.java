package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.BadCoordinateException;
import fr.unice.polytech.startingpoint.exception.IllegalTypeException;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.type.*;

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
    private final GameData gameData;

    //Normal Constructor
    Game(BotType[] botTypes,int nbMission){
        initializeGame();
        gameData = new GameData(botTypes, this,nbMission);
        temporaryInventory = new TemporaryInventory(2);
    }

    private void initializeGame(){
        resource = new Resource();
        board = new Board();
        rules = new Rules(board);
    }

    // Chaque bot joue tant que isContinue est true, et on verifie le nombre de mission faite à chaque tour
    void play() {
        while(gameData.isContinue() && (!resource.isEmpty())) {
            temporaryInventory.reset(2);
            gameData.getBot().botPlay();
            temporaryInventory.hasPlayedCorrectly();
            gameData.missionDone();
            gameData.nextBot();
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

    GameData getGameData() {
        return gameData;
    }

    List<Integer> getScores(){
        return gameData.getScores();
    }

    /**
     * <h1><u>BOT INTERACTIONS</u></h1>
     */

    public void drawCanal() throws OutOfResourcesException, RulesViolationException {
        if (temporaryInventory.add(ActionType.DRAW_CANAL)){
            temporaryInventory.looseStamina();
            gameData.addCanal(resource.drawCanal());
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void drawMission(MissionType missionType) throws OutOfResourcesException, RulesViolationException {
        if (temporaryInventory.add(ActionType.DRAW_MISSION)){
            temporaryInventory.looseStamina();
            gameData.addMission(resource.drawMission(missionType));
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public List<ParcelInformation> drawParcels() throws OutOfResourcesException, RulesViolationException {
        if (temporaryInventory.add(ActionType.DRAW_PARCELS)){
            temporaryInventory.looseStamina();
            temporaryInventory.saveParcels(resource.drawParcels());
            List<ParcelInformation> parcelInformationList = new ArrayList<>();
            for(Parcel parcel : temporaryInventory.getParcelsSaved()){
                parcelInformationList.add(parcel.getParcelInformation());
            }
            return parcelInformationList;
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void selectParcel(ParcelInformation parcelInformation) throws RulesViolationException {
        if (temporaryInventory.add(ActionType.SELECT_PARCEL)){
            if (temporaryInventory.contains(ActionType.DRAW_PARCELS)){
                for (Parcel parcel : temporaryInventory.getParcelsSaved()){
                    if (parcel.getParcelInformation() == parcelInformation){
                        resource.selectParcel(parcel);
                        temporaryInventory.saveParcel(parcel);
                        return;
                    }
                }
                throw new RulesViolationException("Wrong Parcel asked.");
            }
            else
                throw new RulesViolationException("You haven’t drawn.");
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void placeParcel(Coordinate coordinate) throws BadCoordinateException, RulesViolationException {
        if (temporaryInventory.add(ActionType.PLACE_PARCEL)){
            if (temporaryInventory.contains(ActionType.DRAW_PARCELS) && temporaryInventory.contains(ActionType.SELECT_PARCEL)){
                if(rules.isPlayableParcel(coordinate)){
                    board.placeParcel(temporaryInventory.getParcel(),coordinate);
                }
                else{
                    temporaryInventory.remove(ActionType.PLACE_PARCEL);
                    throw new BadCoordinateException("The parcel can't be place on this coordinate : " + coordinate.toString());
                }
            }
            else
                throw new RulesViolationException("You haven’t drawn or selected a parcel.");
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void placeCanal(Coordinate coordinate1, Coordinate coordinate2) throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        if (temporaryInventory.add(ActionType.PLACE_CANAL)) {
            if (rules.isPlayableCanal(coordinate1, coordinate2))
                board.placeCanal(gameData.pickCanal(), coordinate1, coordinate2);
            else{
                temporaryInventory.remove(ActionType.PLACE_CANAL);
                throw new BadCoordinateException("The canal can't be place on these coordinates : " + coordinate1.toString() + " " + coordinate2.toString());
            }
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void moveCharacter(CharacterType characterType, Coordinate coordinate) throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        if (temporaryInventory.add(ActionType.get(characterType))) {
            if (rules.isMovableCharacter(characterType, coordinate)) {
                temporaryInventory.looseStamina();
                gameData.addBamboo(board.moveCharacter(characterType, coordinate));
            }
            else{
                temporaryInventory.remove(ActionType.get(characterType));
                throw new BadCoordinateException("The character can't move to this coordinate : " + coordinate.toString());
            }
        }
        else
            throw new RulesViolationException("Already used this method.");
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
        return gameData.getParcelMissions();
    }

    public List<PandaMission> getInventoryPandaMission() {
        return gameData.getPandaMissions();
    }

    public List<PeasantMission> getInventoryPeasantMission() {
        return gameData.getPeasantMissions();
    }

    public List<Mission> getInventoryMission() {
        return gameData.getMissions();
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
                throw new IllegalTypeException("Wrong ResourceType.");
        }

    }
}